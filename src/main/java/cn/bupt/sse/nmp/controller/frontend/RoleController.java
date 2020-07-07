package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.dao.RoleMapper;
import cn.bupt.sse.nmp.dao.RolePermissionMapper;
import cn.bupt.sse.nmp.entity.Role;
import cn.bupt.sse.nmp.entity.RolePermission;
import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.RoleService;
import javafx.beans.binding.ObjectExpression;
import javafx.geometry.Pos;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-01 13:59
 **/
@RestController
@RequestMapping(value = "/role")
@RequiresRoles("super admin")
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 授予角色权限
     * @param map 存储roleId ：角色ID ; permIds: 权限的集合
     * @return
     */
    @RequestMapping(value = "/assignPerms",method = RequestMethod.PUT)
    public Result assignPerms(@RequestBody Map<String, Object> map){
        Integer roleId = (Integer) map.get("roleId");
        List<Integer> permIds = (List<Integer>) map.get("permIds");
        roleService.assignPerms(roleId,permIds);
        return Result.success("");
    }

    /**
     * 添加角色
     * @param role Role
     * @return
     */
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Result addRole(@RequestBody Role role){
        roleService.addRole(role);
        return Result.success("");
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/updateRole",method = RequestMethod.PUT)
    public Result updateRole(@RequestBody Role role){
        roleService.updateRole(role);
        return Result.success("");
    }

    /**
     * 按照id删除角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/delRole",method = RequestMethod.DELETE)
    public Result delRoleById(@RequestParam  Integer roleId){
        roleService.delRoleById(roleId);
        return Result.success("");
    }

    @RequestMapping(value = "selectAll",method = RequestMethod.GET)
    public Result selectAll(){
        List<Role> roles = roleService.selectAll();
        return Result.success(roles);
    }

}
