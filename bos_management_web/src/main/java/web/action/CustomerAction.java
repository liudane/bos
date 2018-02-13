package web.action;

import crm_service.Customer;
import crm_service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import utils.AliSmsUtil;
import utils.MailUtils;
import utils.Md5Util;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaohui on 19/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="signup", location = "/signup.html"),
        @Result(name="signup_success", location = "/signup-success.html")
})
public class CustomerAction extends BaseAction<Customer>{


    @Action("customerAction_sendcheckcode")
    public String sendcheckcode() throws Exception {

        //生成四位验证码
        String checkcode = RandomStringUtils.randomNumeric(4);
        System.out.printf(checkcode);

        boolean flag = AliSmsUtil.sendMessage(model.getTelephone(), "模板码", checkcode);

        if(flag){
            //将正确的验证码存入session，
            ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), checkcode);
            ServletActionContext.getResponse().getWriter().write("1");
        }else{
            ServletActionContext.getResponse().getWriter().write("0");
        }
        return NONE;
    }



    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Autowired
    private CustomerService customerProxy;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 客户完成注册，保存客户信息、发送短信验证码、激活邮件
     * @return
     * @throws Exception
     */
    @Action("customerAction_regist")
    public String regist() throws Exception {
        try{
            //验证用户验证码是否正确
            //从session中获取真实验证码
            String realCheckcode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
            if(realCheckcode.equals(checkcode)){
                //对客户密码进行MD5加密
                model.setPassword(Md5Util.encode(model.getPassword()));
                customerProxy.save(model);

                //发送短信-通知客户24小时内激活账户
                //通过jms模板向MQ服务器发送邮件信息
                jmsTemplate.send("sms_message", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        MapMessage mapMessage = session.createMapMessage();
                        mapMessage.setString("tel", model.getTelephone());
                        mapMessage.setString("sms_message_code", "sms_119082143");
                        return mapMessage;
                    }
                });
                //发送账户激活链接
                Thread thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        String activeCode = UUID.randomUUID().toString();
                        String content = "恭喜您注册成功，请在24小时内点击链接，激活您的账户</br>"
                                + "<a href='" + MailUtils.activeUrl + "?telephone=" + model.getTelephone() + " &activeCode=" + activeCode + "'>点击激活账户</a>";
                        //给用户发送激活邮件
                        MailUtils.sendMail("欢迎注册速运快递", content, model.getEmail());
                        //将用户正确的验证码存进redis内存中
                        redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);
                    }
                });
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "signup";
        }
        return "signup_success";
    }


    //客户激活账户，url几条参数
    private String activeCode;
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }


    /**
     * 请求从客户邮箱的激活码发来
     * 激活客户账户
     * @return
     */
    @Action("CustomerAction_activeMail")
    public String activeMail()throws Exception{
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        if(StringUtils.isNoneBlank(model.getTelephone()) && StringUtils.isNotBlank(activeCode)){
            //调用CRM查询客户账户状态
            Customer customer = customerProxy.findByTelephone(model.getTelephone());
            if(customer != null){
                if(customer.getType() == null){
                    //未激活
                    //从redis数据库中获取真实激活码
                    String realActiveCode = redisTemplate.opsForValue().get(model.getTelephone());
                    if(StringUtils.isNotBlank(realActiveCode)){
                        //对比激活码是否正确
                        if(activeCode.equals(realActiveCode)){
                            //调用CRM完成客户激活
                            customerProxy.setType2One(customer.getId());
                            ServletActionContext.getResponse().getWriter().write("恭喜您账户激活成功");
                        }else {
                            ServletActionContext.getResponse().getWriter().write("对不起激活码有误");
                        }
                    }else{
                        ServletActionContext.getResponse().getWriter().write("对不起，激活码已失效");
                    }
                }else{
                    ServletActionContext.getResponse().getWriter().write("您的账户已激活");
                }
            }else{
                ServletActionContext.getResponse().getWriter().write("对不起，账户不存在");
            }
        }else{
            ServletActionContext.getResponse().getWriter().write("手机号或者密码有误");
        }
        return NONE;
    }


    /**
     *
     * @return
     * @throws Exception
     */
    @Action("customerAction_login")
    public String login() throws Exception {
        if(StringUtils.isNotBlank(checkcode)){
            String realCheckcode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
            if(checkcode.equals(realCheckcode)){
                //调用CRM查询客户信息
                Customer customer = customerProxy.login(model.getTelephone(), Md5Util.encode(model.getPassword()));
                if(customer != null){
                    //将客户存进session
                    ServletActionContext.getRequest().getSession().setAttribute("loginCustomer", customer);
                    return "index";
                }else{
                    return "login";
                }
            }
        }
        return "login";
    }
}






































