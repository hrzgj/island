package com.verygood.island.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verygood.island.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
* <p>
* 海岛动态 Mapper 接口
* </p>
*
* @author chaos
* @since 2020-05-02
*/
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}
