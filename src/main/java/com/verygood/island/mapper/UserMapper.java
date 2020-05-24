package com.verygood.island.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verygood.island.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* <p>
* 用户 Mapper 接口
* </p>
*
* @author chaos
* @since 2020-05-02
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select nickname from user where user_id =#{id}")
    String getNicknameByUserId(Integer id);

}
