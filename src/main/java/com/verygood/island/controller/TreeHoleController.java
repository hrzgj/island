package com.verygood.island.controller;


import com.verygood.island.entity.TreeHole;
import com.verygood.island.entity.User;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.exception.bizException.BizExceptionCodeEnum;
import com.verygood.island.service.TreeHoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 树洞
 * 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/island/api/v1/tree-hole")
public class TreeHoleController {

    @Autowired
    private TreeHoleService treeHoleService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return new ResultBean<>(treeHoleService.listTreeHolesByPage(page, pageSize));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(treeHoleService.getTreeHoleById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody TreeHole treeHole) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        treeHole.setCreatorId(user.getUserId());
        return new ResultBean<>(treeHoleService.insertTreeHole(treeHole));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(treeHoleService.deleteTreeHoleById(id,user.getUserId()));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody TreeHole treeHole) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        treeHole.setCreatorId(user.getUserId());
        return new ResultBean<>(treeHoleService.updateTreeHole(treeHole));
    }

    /**
     * 根据用户id查询
     */
    @RequestMapping(method = RequestMethod.GET,value = "/me")
    public ResultBean<?> getByUserId() {
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(treeHoleService.getByUserId(user.getUserId()));
    }

}
