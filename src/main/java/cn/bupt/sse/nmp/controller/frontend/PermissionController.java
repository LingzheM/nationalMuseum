package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.entity.Permission;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 权限管理
 */
@RestController
@RequestMapping(value = "/permission")
@RequiresRoles("super admin")
@Api(tags = "权限管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 添加权限
     * @param permission
     * @return
     */
    @ApiOperation(value = "添加权限", notes = "无需填写permissionId")
    @PostMapping(value = "add")
    public Result addPermission(@RequestBody Permission permission){
        permissionService.addPermission(permission);
        return Result.success("");
    }

    /**
     * 删除权限
     * @param permissionId
     * @return
     */
    @ApiOperation(value = "根据id删除权限")
    @PostMapping(value = "delete")
    public Result deletePermission(@RequestParam Integer permissionId){
        permissionService.deletePermission(permissionId);
        return Result.success("");
    }

    /**
     * 修改权限
     * @param permission
     * @return
     */
    @ApiOperation(value = "修改权限")
    @PostMapping(value = "update")
    public Result updatePermission(@RequestBody Permission permission){
        permissionService.updatePermission(permission);
        return Result.success("");
    }

    /**
     * 查询全部权限
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询全部权限")
    @GetMapping(value = "select")
    public Result selectPermission(@RequestParam(defaultValue = "1") int pageNum,
                                   @RequestParam(defaultValue = "10") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Permission> pageInfo = new PageInfo<>(permissionService.selectAll());
        return Result.success(pageInfo);
    }
}
