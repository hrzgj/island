package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.Letter;

/**
* <p>
* 信件 服务类
* </p>
*
* @author chaos
* @since 2020-05-02
*/
public interface LetterService {

    /**
    * 分页查询Letter
    *
    * @param page     当前页数
    * @param pageSize 页的大小
    * @param keyword  搜索关键词
    * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
    * @author chaos
    * @since 2020-05-02
    */
    Page<Letter> listLettersByPage(int page, int pageSize, String keyword);

    /**
    * 根据id查询Letter
    *
    * @param id 需要查询的Letter的id
    * @return 返回对应id的Letter对象
    * @author chaos
    * @since 2020-05-02
    */
    Letter getLetterById(int id);

    /**
    * 插入Letter
    *
    * @param letter 需要插入的Letter对象
    * @return 返回插入成功之后Letter对象的id
    * @author chaos
    * @since 2020-05-02
    */
    int insertLetter(Letter letter);

    /**
    * 根据id删除Letter
    *
    * @param id 需要删除的Letter对象的id
    * @return 返回被删除的Letter对象的id
    * @author chaos
    * @since 2020-05-02
    */
    int deleteLetterById(int id);

    /**
    * 根据id更新Letter
    *
    * @param letter 需要更新的Letter对象
    * @return 返回被更新的Letter对象的id
    * @author chaos
    * @since 2020-05-02
    */
    int updateLetter(Letter letter);

}
