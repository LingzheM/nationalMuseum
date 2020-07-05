package cn.bupt.sse.nmp.shiro;

import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.jwt.JWTToken;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.UserService;
import cn.bupt.sse.nmp.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    /**
     * 这是个必须重写的方法
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //return super.supports(token);
        return token instanceof JWTToken;
    }

    /**
     * 需要检测用户权限的时候调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();

        // 通过解码获取userPhone
        String userPhone = JWTUtil.getUserPhone(token);

        Result<User> userResult = userService.getUserByPhone(userPhone);

        if (userResult.getData().equals(null)) {
            throw new AuthenticationException("用户不存在");
        }

        if (!JWTUtil.verify(token, userPhone, userResult.getData().getPassword())) {
            throw new AuthenticationException("用户名或密码不正确");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");

    }
}
