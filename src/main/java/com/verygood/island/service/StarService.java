package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Star;
import com.verygood.island.entity.vo.UserVo;

/**
 * <p>
 * 星标
 * 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface StarService {

    /**
     * 分页查询Star
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param factor   搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    Page<UserVo> listStarsByPage(int page, int pageSize, String factor, Integer userId);


    /**
     * 根据id查询Star
     *
     * @param id 需要查询的Star的id
     * @return 返回对应id的Star对象
     * @author chaos
     * @since 2020-05-04
     */
    Star getStarById(int id);

    /**
     * 插入Star
     *
     * @param star 需要插入的Star对象
     * @param userId 用户id
     * @return 返回插入成功之后Star对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertStar(Star star, Integer userId);

    /**
     * 根据id删除Star
     *
     * @param id 需要删除的Star对象的id
     * @return 返回被删除的Star对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deleteStarById(int id);

    /**
     * 根据id更新Star
     *
     * @param star 需要更新的Star对象
     * @return 返回被更新的Star对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updateStar(Star star);

}
