package com.verygood.island.controller;


import com.verygood.island.entity.Message;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 树洞留言 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        return new ResultBean<>(messageService.listMessagesByPage(page, pageSize, factor));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(messageService.getMessageById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Message message) {
        return new ResultBean<>(messageService.insertMessage(message));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(messageService.deleteMessageById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Message message) {
        return new ResultBean<>(messageService.updateMessage(message));
    }
}
