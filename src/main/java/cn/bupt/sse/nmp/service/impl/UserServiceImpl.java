package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.UserMapper;
import cn.bupt.sse.nmp.dao.UserRoleMapper;
import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.entity.UserRole;
import cn.bupt.sse.nmp.exception.UnAuthorizedException;
import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.UserService;
import cn.bupt.sse.nmp.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Result<LoginUser> login(String userPhone, String password) {
        User user = userMapper.selectByUserPhone(userPhone);
        if (user != null) {
            password = new Md5Hash(password, user.getSalt(), 1024).toHex();
            if (user.getPassword().equals(password)) {
                LoginUser loginUser = new LoginUser();
                loginUser.setUser(user);
                String token = JWTUtil.sign(userPhone, password);
                loginUser.setToken(token);
                loginUser.getUser().setPassword(null);
                loginUser.getUser().setSalt(null);
                return Result.success(loginUser);
            } else {
                return Result.error(CodeMsg.PASSWORD_INCORRECT);
            }
        } else {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
    }

    @Override
    public Result<User> getUserByPhone(String userPhone) {
        User user = userMapper.selectByUserPhone(userPhone);
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        } else {
            return Result.success(user);
        }
    }

    /**
     * 注册功能
     *
     * @param user
     * @return
     */
    @Override
    public Result<Integer> UserRegister(User user) {


        String userPhone = user.getPhone();

        User aUser = userMapper.selectByUserPhone(userPhone);

        if (aUser != null && aUser.getPhone().equals(userPhone)) {
            return Result.error(CodeMsg.USER_PHONE_EXISTED);
        } else {
            String salt = UUID.randomUUID().toString();
            Md5Hash password = new Md5Hash(user.getPassword(), salt, 1024);
            user.setSalt(salt);
            user.setPassword(password.toHex());
            Integer resultCode = userMapper.insert(user);
            if (resultCode != 1) {
                throw new RuntimeException("用户表添加失败");
            }
            Integer userId = userMapper.selectByUserPhone(userPhone).getUserId();
            UserRole userRole = new UserRole();
            userRole.setRoleId(3);
            userRole.setUserId(userId);
            resultCode = userRoleMapper.insert(userRole);
            if (resultCode != 1) {
                throw new RuntimeException("用户角色表添加失败");
            }

            return Result.success(resultCode);
        }
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUserById(Integer userId) {
        userMapper.deleteUserById(userId);
    }

    @Override
    public List<Map> selectUser(String userPhone) {
        return userMapper.selectUser(userPhone);
    }


}
