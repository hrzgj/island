package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Post;

/**
 * <p>
 * 海岛动态 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface PostService {

    /**
     * 分页查询Post
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param factor   搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    Page<Post> listPostsByPage(int page, int pageSize, String factor);

    /**
     * 根据id查询Post
     *
     * @param id 需要查询的Post的id
     * @return 返回对应id的Post对象
     * @author chaos
     * @since 2020-05-04
     */
    Post getPostById(int id);

    /**
     * 插入Post
     *
     * @param post 需要插入的Post对象
     * @return 返回插入成功之后Post对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertPost(Post post);

    /**
     * 根据id删除Post
     *
     * @param id 需要删除的Post对象的id
     * @return 返回被删除的Post对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deletePostById(int id);

    /**
     * 根据id更新Post
     *
     * @param post 需要更新的Post对象
     * @return 返回被更新的Post对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updatePost(Post post);

}
