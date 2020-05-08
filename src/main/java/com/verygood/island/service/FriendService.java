package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Friend;
import com.verygood.island.entity.User;

import java.util.List;

/**
 * <p>
 * 笔友 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface FriendService {

    /**
     * 分页查询Friend
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    Page<Friend> listFriendsByPage(int page, int pageSize,Integer userId);

    /**
     * 根据id查询Friend
     *
     * @param id 需要查询的Friend的id
     * @return 返回对应id的Friend对象
     * @author chaos
     * @since 2020-05-04
     */
    Friend getFriendById(int id);

    /**
     * 插入Friend
     *
     * @param friend 需要插入的Friend对象
     * @return 返回插入成功之后Friend对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertFriend(Friend friend);

    /**
     * 根据id删除Friend
     *
     * @param id 需要删除的Friend对象的id
     * @return 返回被删除的Friend对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deleteFriendById(int id);

    /**
     * 根据id更新Friend
     *
     * @param friend 需要更新的Friend对象
     * @return 返回被更新的Friend对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updateFriend(Friend friend);



    List<User> getUserFriend(Integer userId);

}
