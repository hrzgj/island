package com.verygood.island.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verygood.island.entity.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 笔友 Mapper 接口
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Mapper
@Repository
public interface FriendMapper extends BaseMapper<Friend> {


}
