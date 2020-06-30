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
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/register")
    public Result<LoginUser> register(@RequestParam User user, HttpServletRequest request, HttpServletResponse response) {

        return Result.error(CodeMsg.USER_NOT_EXIST);
    }

    /**
     * 登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public Result<LoginUser> login(@RequestParam User user, HttpServletRequest request, HttpServletResponse response) {

        // 获取用户手机号和密码
        Integer userPhone = user.getPhone();
        String password = user.getPassword();

        return userService.login(userPhone, password);
    }


    @RequestMapping("/getInfo")
    public Result<String> getInfo(HttpServletRequest request, HttpServletResponse response) {

        return Result.success("");
    }

}
