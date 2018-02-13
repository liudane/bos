package web.realm;

import dao.UserDao;
import domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhaohui on 14/01/2018.
 */
@Component("bosRealm")
public class BosRealm extends AuthorizingRealm{


    @Autowired
    private UserDao userDao;
    /**
     * 授权
     * shiro提供四种授权方式：验证用户是有有权限/角色，realm中的授权方法都会在执行
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.printf("给用户授权");
        //创建当前用户授权信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户动态，查询用户角色/权限
        info.addStringPermission("standard_page");
        info.addStringPermission("standard_save");
        info.addRole("admin");

        return info;
    }


    /**
     * 认证
     * 参数Token：就是subject  login方法提交的用户名密码口令
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.printf("进行用户认证");
        //根据用户输入的用户名，查询数据库中的密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        //根据用户名查询数据库
        User user = userDao.findByUsername(username);

        if(user == null){
            //返回用户名错误
            return null;//框架会抛出异常，未知账户异常
        }
        //对比密码是否正确，交给shiro框架--对比失败，shiro自动抛出异常
        //参数一：主角；         参数二：真实密码   参数三：realm名称
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());

        return info;
    }
}


















