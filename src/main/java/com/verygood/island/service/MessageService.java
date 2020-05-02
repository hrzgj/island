package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Message;

/**
 * <p>
 * 树洞留言 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
public interface MessageService {

    /**
     * 分页查询Message
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param keyword  搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-02
     */
    Page<Message> listMessagesByPage(int page, int pageSize, String keyword);

    /**
     * 根据id查询Message
     *
     * @param id 需要查询的Message的id
     * @return 返回对应id的Message对象
     * @author chaos
     * @since 2020-05-02
     */
    Message getMessageById(int id);

    /**
     * 插入Message
     *
     * @param message 需要插入的Message对象
     * @return 返回插入成功之后Message对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int insertMessage(Message message);

    /**
     * 根据id删除Message
     *
     * @param id 需要删除的Message对象的id
     * @return 返回被删除的Message对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int deleteMessageById(int id);

    /**
     * 根据id更新Message
     *
     * @param message 需要更新的Message对象
     * @return 返回被更新的Message对象的id
     * @author chaos
     * @since 2020-05-02
     */
    int updateMessage(Message message);

}
