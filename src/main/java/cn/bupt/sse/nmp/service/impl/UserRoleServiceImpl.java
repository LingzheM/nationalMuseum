package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.UserRoleMapper;
import cn.bupt.sse.nmp.entity.UserRole;
import cn.bupt.sse.nmp.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public void update(UserRole userRole) {
        userRoleMapper.update(userRole);
    }
}
