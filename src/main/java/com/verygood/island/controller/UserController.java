package com.verygood.island.controller;


import com.verygood.island.entity.User;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.exception.bizException.BizExceptionCodeEnum;
import com.verygood.island.security.shiro.token.UserToken;
import com.verygood.island.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 搜索分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> searchByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(name = "factor") String factor) {
        return new ResultBean<>(userService.searchUsersByPage(page, pageSize, factor));
    }



    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(userService.getUserById(id));
    }

    /**
     * 随机获取
     */
    @RequestMapping(method = RequestMethod.GET, value = "/random")
    public ResultBean<?> getByRandom() {
        return new ResultBean<>(userService.getUserByRandom());
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody User user) {
        return new ResultBean<>(userService.insertUser(user));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(userService.deleteUserById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody User user) {
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        user.setUserId(principal.getUserId());
        return new ResultBean<>(userService.updateUser(user));
    }

    /**
     * 登录
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResultBean<?> login(@RequestBody User user, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UserToken(user.getUsername(), user.getPassword()));
        user = (User) subject.getPrincipal();
        response.setHeader("Authorization", subject.getSession().getId().toString());
        return new ResultBean<>(user);
    }

    /**
     * 上传头像
     */
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResultBean<?> login(@RequestParam MultipartFile file) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(userService.uploadIcon(file, user.getUserId()));
    }
}
