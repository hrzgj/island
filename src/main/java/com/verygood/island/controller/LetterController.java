package com.verygood.island.controller;


import com.verygood.island.entity.Letter;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 信件 前端控制器
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 * @version v1.0
 */
@RestController
@RequestMapping("/island/api/v1/letter")
public class LetterController {

    @Autowired
    private LetterService letterService;

    /**
    * 查询分页数据
    */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam String keyword) {
        return new ResultBean<>(letterService.listLettersByPage(page, pageSize,keyword));
    }


    /**
    * 根据id查询
    */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(letterService.getLetterById(id));
    }

    /**
    * 新增
    */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Letter letter) {
        return new ResultBean<>(letterService.insertLetter(letter));
    }

    /**
    * 删除
    */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(letterService.deleteLetterById(id));
    }

    /**
    * 修改
    */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Letter letter) {
        return new ResultBean<>(letterService.updateLetter(letter));
    }
}
