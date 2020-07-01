package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.dao.RoleMapper;
import cn.bupt.sse.nmp.entity.Role;
import cn.bupt.sse.nmp.entity.RolePermission;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.RoleService;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        return Result.success(null);
    }

    //测试mapper
    @Autowired
    private RoleMapper roleMapper;
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Role get(){
        Role byId = roleMapper.findById(1);
        return byId;
    }


}
