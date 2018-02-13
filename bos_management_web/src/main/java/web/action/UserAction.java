package web.action;

import domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.UserService;
import utils.Md5Util;

import javax.servlet.http.HttpSession;

/**
 * Created by zhaohui on 14/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="login", location = "/login.jsp"),
        @Result(name="index", type="redirect", location="/index.jsp")
})
public class UserAction extends BaseAction<User>{

    @Autowired
    private UserService userService;

    //用户验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    /**
     * 基于shiro框架实现用户认证
     * @return
     * @throws Exception
     */
    @Action("userAction_login")
    public String login() throws Exception {
        String realCheckcode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        if(StringUtils.isNotBlank(checkcode)){
            if(checkcode.equals(realCheckcode)){

                //开始登陆
                //通过工具类获取到subject对象“用户“---有状态：未认证
                Subject subject = SecurityUtils.getSubject();
                //用户名密码口令
                UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), Md5Util.encode(model.getPassword()));
                try{
                    subject.login(token);
                }catch (Exception e){
                    if(e instanceof UnknownAccountException){
                        this.addActionError("用户名错误");//jsp页面中<s:actionError>
                        System.out.printf("用户名错误");
                    }
                    if(e instanceof IncorrectCredentialsException){
                        this.addActionError("密码错误");//jsp页面中<s:actionError>
                        System.out.printf("密码错误");
                    }
                    e.printStackTrace();
                    System.out.printf("用户名或者密码错误");
                    return "login";
                }

                //登陆成功
                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
                return "index";
            }
        }
        return "login";
    }


    /**
     * 退出账户
     * @return
     * @throws Exception
     */
    @Action("userAction_logout")
    public String logout() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        //shiro框架的logout方法会自动将session域中的user删除，不用手动删除
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.getAttribute("loginUser");

        return "login";
    }
}




































