package com.verygood.island.controller;


import com.verygood.island.entity.Reply;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 回复 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        return new ResultBean<>(replyService.listReplysByPage(page, pageSize, factor));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(replyService.getReplyById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Reply reply) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        reply.setWriterId(user.getUserId());
        return new ResultBean<>(replyService.insertReply(reply));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(replyService.deleteReplyById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Reply reply) {
        return new ResultBean<>(replyService.updateReply(reply));
    }

    /**
     * 根据postId查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/post/{id}")
    public ResultBean<?> getByPostId(@PathVariable("id") Integer id) {
        return new ResultBean<>(replyService.getByPostId(id));
    }
}
