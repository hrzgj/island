package com.verygood.island.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.verygood.island.entity.User;
import com.verygood.island.entity.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
public interface UserService {


    /**
     * 根据id查询User
     *
     * @param user 登陆用户
     * @return 返回登陆成功的用户
     * @author chaos
     * @since 2020-05-02
     */
    User login(User user);


    /**
     * 分页查询User
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @param factor   搜索关键词
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author chaos
     * @since 2020-05-04
     */
    Page<UserVo> searchUsersByPage(int page, int pageSize, String factor);

    /**
     * 根据id查询User
     *
     * @param id 需要查询的User的id
     * @return 返回对应id的User对象
     * @author chaos
     * @since 2020-05-04
     */
    UserVo getUserById(int id);


    /**
     * 随机获取用户
     *
     * @return 返回一个用户
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-23
     */
    UserVo getUserByRandom();

    /**
     * 插入User
     *
     * @param user 需要插入的User对象
     * @return 返回插入成功之后User对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int insertUser(User user);

    /**
     * 根据id删除User
     *
     * @param id 需要删除的User对象的id
     * @return 返回被删除的User对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int deleteUserById(int id);

    /**
     * 根据id更新User
     *
     * @param user 需要更新的User对象
     * @return 返回被更新的User对象的id
     * @author chaos
     * @since 2020-05-04
     */
    int updateUser(User user);

    /**
     * 上传用户头像
     *
     * @param file   头像文件
     * @param userId 用户Id信息
     * @return : java.lang.String
     * @author : huange7
     * @date : 2020-05-07 19:52
     */
    String uploadIcon(MultipartFile file, Integer userId);

    /**
     * 上传用户的海岛背景
     * @param file 背景文件
     * @param userId 用户id
     * @return 文件位置
     */
    String uploadBackground(MultipartFile file, Integer userId);

}
