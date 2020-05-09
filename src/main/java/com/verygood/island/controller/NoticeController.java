package com.verygood.island.controller;


import com.verygood.island.entity.Notice;
import com.verygood.island.entity.User;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.exception.bizException.BizExceptionCodeEnum;
import com.verygood.island.service.NoticeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 通知 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage() {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(noticeService.listNoticesByPage(user.getUserId()));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(noticeService.getNoticeById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Notice notice) {
        return new ResultBean<>(noticeService.insertNotice(notice));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(noticeService.deleteNoticeById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Notice notice) {
        return new ResultBean<>(noticeService.updateNotice(notice));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/read/{id}")
    public ResultBean<?> readById(@PathVariable("id") Integer id){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            return new ResultBean<>(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(noticeService.readNoticeById(user.getUserId(), id));
    }
}
