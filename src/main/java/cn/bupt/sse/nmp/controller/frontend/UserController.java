package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.entity.UserRole;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.UserRoleService;
import cn.bupt.sse.nmp.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiresRoles("super admin")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation(value = "查询用户", notes = "根据用户名，角色名，角色手机号进行模糊查询")
    @GetMapping(value = "/select")
    public Result selectAll(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "10") int pageSize,
                            @RequestParam String userInfo){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Map> pageInfo = new PageInfo<>(userService.selectUser(userInfo));
        return Result.success(pageInfo);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping(value = "/update")
    public Result updateRole(@RequestBody User user){
        userService.updateUser(user);
        return Result.success("");
    }

    @ApiOperation(value = "根据id删除用户", notes = "只需传入userId")
    @PostMapping(value = "/delete")
    public Result delRoleById(@RequestBody User user){
        userService.deleteUserById(user.getUserId());
        return Result.success("");
    }

    @ApiOperation(value = "修改用户的角色")
    @PostMapping(value = "/updateRole")
    public Result updateUserRole(@RequestBody UserRole userRole){
        userRoleService.update(userRole);
        return Result.success("");
    }

}
