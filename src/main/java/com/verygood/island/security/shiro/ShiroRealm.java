package com.verygood.island.security.shiro;


import com.verygood.island.entity.User;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.security.shiro.token.UserToken;
import com.verygood.island.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //TODO 用户权限列表,普通信息等...
        //return info;
        AuthorizationInfo info = new SimpleAuthorizationInfo();
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        log.info(primaryPrincipal);
        info.getStringPermissions().add("all");
        return info;
    }

    /**
     * 鉴权   openid 判断是否用户是否已经绑定微信
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        if (token instanceof UserToken) {
            try {
                UserToken userToken = (UserToken) token;
                if (null == userToken.getPassword()) {
                    throw new BizException("登录失败!请输入正确的密码！");
                }
                User loginUser = new User();
                loginUser.setUsername(userToken.getUsername());
                loginUser.setPassword(String.valueOf(userToken.getPassword()));
                User user = userService.login(loginUser);
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, "ok", this.getClass().getSimpleName());
                return authcInfo;
            } catch (BizException e) {
                throw new AuthenticationException(e.getMessage());
            }
        }
        return null;

    }
}