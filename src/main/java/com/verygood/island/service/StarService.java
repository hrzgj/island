package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Star;

/**
 * <p>
 * 星标
 * 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
public interface StarService {

    /**
     * 分页查询Star
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param keyword  搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-02
     */
    Page<Star> listStarsByPage(int page, int pageSize, String keyword);

    /**
     * 根据id查询Star
     *
     * @param id 需要查询的Star的id
     * @return 返回对应id的Star对象
     * @author chaos
     * @since 2020-05-02
     */
    Star getStarById(int id);

    /**
     * 插入Star
     *
     * @param star 需要插入的Star对象
     * @return 返回插入成功之后Star对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int insertStar(Star star);

    /**
     * 根据id删除Star
     *
     * @param id 需要删除的Star对象的id
     * @return 返回被删除的Star对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int deleteStarById(int id);

    /**
     * 根据id更新Star
     *
     * @param star 需要更新的Star对象
     * @return 返回被更新的Star对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int updateStar(Star star);

}
