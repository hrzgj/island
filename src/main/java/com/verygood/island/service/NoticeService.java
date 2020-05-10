package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Notice;

import java.util.List;

/**
 * <p>
 * 通知 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface NoticeService {

    /**
     * 分页查询Notice
     *
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    List<Notice> listNoticesByPage(Integer userId);

    /**
     * 根据id查询Notice
     *
     * @param id 需要查询的Notice的id
     * @return 返回对应id的Notice对象
     * @author chaos
     * @since 2020-05-04
     */
    Notice getNoticeById(int id);

    /**
     * 插入Notice
     *
     * @param notice 需要插入的Notice对象
     * @return 返回插入成功之后Notice对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertNotice(Notice notice);

    /**
     * 根据id删除Notice
     *
     * @param id 需要删除的Notice对象的id
     * @return 返回被删除的Notice对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deleteNoticeById(int id);

    /**
     * 根据id更新Notice
     *
     * @param notice 需要更新的Notice对象
     * @return 返回被更新的Notice对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updateNotice(Notice notice);

    /**
     * 根据用户id和消息id更新消息的读状态
     * @param userId 用户id
     * @param noticeId 消息id
     * @return : int
     * @author : huange7
     * @date : 2020-05-10 0:29
     */
    int readNoticeById(Integer userId, Integer noticeId);

}
