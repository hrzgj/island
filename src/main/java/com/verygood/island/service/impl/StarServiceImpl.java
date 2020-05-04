package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Star;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.StarMapper;
import com.verygood.island.service.StarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 星标
 * 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {

    @Override
    public Page<Star> listStarsByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询star: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<Star> queryWrapper = new QueryWrapper<Star>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Star> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询star完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public Star getStarById(int id) {
        log.info("正在查询star中id为{}的数据", id);
        Star star = super.getById(id);
        log.info("查询id为{}的star{}", id, (null == star ? "无结果" : "成功"));
        return star;
    }

    @Override
    public int insertStar(Star star) {
        log.info("正在插入star");
        if (super.save(star)) {
            log.info("插入star成功,id为{}", star.getStarId());
            return star.getStarId();
        } else {
            log.error("插入star失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteStarById(int id) {
        log.info("正在删除id为{}的star", id);
        if (super.removeById(id)) {
            log.info("删除id为{}的star成功", id);
            return id;
        } else {
            log.error("删除id为{}的star失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateStar(Star star) {
        log.info("正在更新id为{}的star", star.getStarId());
        if (super.updateById(star)) {
            log.info("更新d为{}的star成功", star.getStarId());
            return star.getStarId();
        } else {
            log.error("更新id为{}的star失败", star.getStarId());
            throw new BizException("更新失败[id=" + star.getStarId() + "]");
        }
    }

}
