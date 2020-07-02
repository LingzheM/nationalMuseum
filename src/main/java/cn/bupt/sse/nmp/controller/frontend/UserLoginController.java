package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/frontend")
public class UserLoginController {

    @Autowired
    UserService userService;

    /**
     * 注册
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/register")
    public Result<User> register(HttpServletRequest request, HttpServletResponse response) {
        // 获取注册手机号、用户名和密码
        String userPhone = request.getParameter("userPhone");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        // 创建一个用户
        User user = new User();
        user.setPhone(userPhone);
        user.setPassword(password);
        user.setUserName(userName);

        Result<User> result = userService.UserRegister(user);

        return result;
    }

    /**
     * 登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public Result<LoginUser> login(HttpServletRequest request, HttpServletResponse response) {

        // 获取用户手机号和密码
        String userPhone = request.getParameter("userPhone");
        String password = request.getParameter("password");

        return userService.login(userPhone, password);
    }


    @RequestMapping("/getInfo")
    public Result<String> getInfo(HttpServletRequest request, HttpServletResponse response) {
        return Result.success("");
    }

}
