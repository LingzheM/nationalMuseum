package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/frontend")
@Api(tags = "登陆注册")
public class UserLoginController {

    @Autowired
    UserService userService;

    /**
     * @param user
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "无需添加用户id和创建时间")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/register")
    public Result<Integer> register(@RequestBody User user) {
        Result<Integer> result = userService.UserRegister(user);
        return result;
    }

    /**
     * @param userPhone
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result<LoginUser> login(@RequestParam String userPhone,
                                   @RequestParam String password) {
        return userService.login(userPhone, password);
    }


    @PostMapping("/getInfo")
    public Result<String> getInfo(HttpServletRequest request, HttpServletResponse response) {
        return Result.success("");
    }

}
