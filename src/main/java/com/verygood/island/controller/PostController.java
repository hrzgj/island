package com.verygood.island.controller;


import com.verygood.island.entity.Post;
import com.verygood.island.entity.User;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.exception.bizException.BizExceptionCodeEnum;
import com.verygood.island.service.PostService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 海岛动态 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        return new ResultBean<>(postService.listPostsByPage(page, pageSize, factor));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(postService.getPostById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Post post) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        post.setUserId(user.getUserId());
        return new ResultBean<>(postService.insertPost(post));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(postService.deletePostById(id, user.getUserId()));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Post post) {
        return new ResultBean<>(postService.updatePost(post));
    }

    /**
     * 根据海岛id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all/{id}")
    public ResultBean<?> getByUserId(@PathVariable("id") Integer id) {
        return new ResultBean<>(postService.getByUserId(id));
    }
}
