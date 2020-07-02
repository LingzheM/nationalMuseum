package cn.bupt.sse.nmp.filter;

import cn.bupt.sse.nmp.jwt.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 所有的请求都会先经过Filter，所以我们继承官方的Basic，然后重写鉴权的方法
 * 执行流程: preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {


    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));

        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");

        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-"));
        // 跨域时，首先会发送一个option请求，这里给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }


    /**
     * 解释说明为什么最终返回的都是true（允许访问）
     * 例如提供一个地址 /article
     * 登录用户和普通游客看到的内容是不同的
     * 如果返回了false，请求会被直接拦截，用户看不到东西
     * 所以这里返回true，controller中通过subject.isAuthenticated()来判断登录
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                response401(request, response);
            }
        }
        return true;
    }

    /**
     * 判断用户是否想要登录
     * 检测header里面是否包含authorization
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String authorization = httpServletRequest.getHeader("Authorization");

        JWTToken token = new JWTToken(authorization);

        // 交给realm进行登录
        getSubject(request, response).login(token);
        // 如果没有抛出异常，就表示成功登录，返回true
        return true;
    }


    private void response401(ServletRequest request, ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse)response;

            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {

        }
    }
}
