package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Letter;
import com.verygood.island.entity.vo.LetterVo;

import java.util.List;

/**
 * <p>
 * 信件 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface LetterService {

    /**
     * 分页查询Letter
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param factor   搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    Page<LetterVo> listLettersByPage(int page, int pageSize, Integer friendId, Integer userId);

    /**
     * 根据id查询Letter
     *
     * @param id 需要查询的Letter的id
     * @return 返回对应id的Letter对象
     * @author chaos
     * @since 2020-05-04
     */
    Letter getLetterById(int id);

    /**
     * 插入Letter
     *
     * @param letter 需要插入的Letter对象
     * @return 返回插入成功之后Letter对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertLetter(Letter letter);

    /**
     * 根据id删除Letter
     *
     * @param id 需要删除的Letter对象的id
     * @return 返回被删除的Letter对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deleteLetterById(int id);

    /**
     * 根据id更新Letter
     *
     * @param letter 需要更新的Letter对象
     * @return 返回被更新的Letter对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updateLetter(Letter letter);


    /**
     * 得到一个笔友互发的信件
     *
     * @param letter Letter对象
     * @return 返回信件
     * @author cy
     * @since 2020-05-04
     */
    List<LetterVo> getOneFriendLetter(Integer senderId,Integer receiverId);

    /**
     * 获取用户的草稿列表
     * @param userId 用户id
     * @return : java.util.List<com.verygood.island.entity.Letter>
     * @author : huange7
     * @date : 2020-05-14 21:01
     */
    List<Letter> getLetterDraft(Integer userId);

    /**
     * 发送时间胶囊
     * @param letter 信件实体类
     * @param userId 发送者id
     * @return
     */
    int sendCapsuleLetter(Letter letter, Integer userId);

}
