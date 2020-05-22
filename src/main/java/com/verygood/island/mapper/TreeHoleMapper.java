package com.verygood.island.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verygood.island.entity.TreeHole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 树洞
 * Mapper 接口
 * </p>
 *
 * @author chaos
 * @since 2020-05-21
 */
@Mapper
@Repository
public interface TreeHoleMapper extends BaseMapper<TreeHole> {

    @Select("select count(0) from tree_hole where creator_id =#{userId}")
    Integer getUserTreeNumber(Integer userId);

    @Select("select creator_id from tree_hole where tree_hole_id=#{treeId}")
    Integer getUserIdByTreeId(Integer treeId);
}
