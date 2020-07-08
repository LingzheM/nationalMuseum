package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.UserNumInfo;

public interface UserNumInfoMapper {
    int insert(UserNumInfo record);

    int insertSelective(UserNumInfo record);
}