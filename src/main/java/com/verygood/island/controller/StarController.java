package com.verygood.island.controller;


import com.verygood.island.entity.Star;
import com.verygood.island.entity.User;
import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.exception.bizException.BizExceptionCodeEnum;
import com.verygood.island.service.StarService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 星标
 * 前端控制器
 * </p>
 *
 * @author chaos
 * @version v1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/island/api/v1/star")
public class StarController {

    @Autowired
    private StarService starService;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (null == user) {
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(starService.listStarsByPage(page, pageSize, factor, user.getUserId()));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Integer id) {
        return new ResultBean<>(starService.getStarById(id));
    }

    /**
     * 根据id星标海岛
     */
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody Star star) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new BizException(BizExceptionCodeEnum.NO_LOGIN);
        }
        return new ResultBean<>(starService.insertStar(star, user.getUserId()));
    }

    /**
     * 删除
     */
    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Integer id) {
        return new ResultBean<>(starService.deleteStarById(id));
    }

    /**
     * 修改
     */
    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody Star star) {
        return new ResultBean<>(starService.updateStar(star));
    }
}
