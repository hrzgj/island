package com.verygood.island.controller;


import com.verygood.island.entity.Friend;
import com.verygood.island.entity.User;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.exception.bizException.BizExceptionCodeEnum;
import com.verygood.island.service.FriendService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 笔友 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(friendService.listFriendsByPage(page, pageSize,user.getUserId()));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(friendService.getFriendById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Friend friend) {
        return new ResultBean<>(friendService.insertFriend(friend));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(friendService.deleteFriendById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Friend friend) {
        return new ResultBean<>(friendService.updateFriend(friend));
    }



    /*
     *获得所有好友
     */
    @RequestMapping(method = RequestMethod.GET,value = "/all")
    public ResultBean<?> getUserFriend() {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(friendService.getUserFriend(user.getUserId()));
    }

}
