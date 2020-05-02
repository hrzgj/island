package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Notice;

/**
 * <p>
 * 通知 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
public interface NoticeService {

    /**
     * 分页查询Notice
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param keyword  搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-02
     */
    Page<Notice> listNoticesByPage(int page, int pageSize, String keyword);

    /**
     * 根据id查询Notice
     *
     * @param id 需要查询的Notice的id
     * @return 返回对应id的Notice对象
     * @author chaos
     * @since 2020-05-02
     */
    Notice getNoticeById(int id);

    /**
     * 插入Notice
     *
     * @param notice 需要插入的Notice对象
     * @return 返回插入成功之后Notice对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int insertNotice(Notice notice);

    /**
     * 根据id删除Notice
     *
     * @param id 需要删除的Notice对象的id
     * @return 返回被删除的Notice对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int deleteNoticeById(int id);

    /**
     * 根据id更新Notice
     *
     * @param notice 需要更新的Notice对象
     * @return 返回被更新的Notice对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int updateNotice(Notice notice);

}
