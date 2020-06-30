package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.result.Result;

public interface UserService {

    public Result<LoginUser> login(Integer userPhone, String password);

}
