package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Stamp;

/**
 * <p>
 * 邮票 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface StampService {

    /**
     * 分页查询Stamp
     * @param userId 用户id
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param factor   搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    Page<Stamp> listStampsByPage(int page, int pageSize, String factor,Integer userId);

    /**
     * 根据id查询Stamp
     *
     * @param id 需要查询的Stamp的id
     * @return 返回对应id的Stamp对象
     * @author chaos
     * @since 2020-05-04
     */
    Stamp getStampById(int id);

    /**
     * 插入Stamp
     *
     * @param stamp 需要插入的Stamp对象
     * @return 返回插入成功之后Stamp对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertStamp(Stamp stamp);

    /**
     * 根据id删除Stamp
     *
     * @param id 需要删除的Stamp对象的id
     * @return 返回被删除的Stamp对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deleteStampById(int id);

    /**
     * 根据id更新Stamp
     *
     * @param stamp 需要更新的Stamp对象
     * @return 返回被更新的Stamp对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updateStamp(Stamp stamp);

}
