package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Stamp;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.StampMapper;
import com.verygood.island.service.StampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮票 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class StampServiceImpl extends ServiceImpl<StampMapper, Stamp> implements StampService {

    @Override
    public Page<Stamp> listStampsByPage(int page, int pageSize, String factor, Integer userId) {
        log.info("正在执行分页查询stamp: page = {} pageSize = {} factor = {} userId = {} ", page, pageSize, factor, userId);
        QueryWrapper<Stamp> queryWrapper = new QueryWrapper<Stamp>().eq("user_id", userId);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Stamp> result = super.page(new Page<>(page, pageSize), queryWrapper);
        log.info("分页查询stamp完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public Stamp getStampById(int id) {
        log.info("正在查询stamp中id为{}的数据", id);
        Stamp stamp = super.getById(id);
        log.info("查询id为{}的stamp{}", id, (null == stamp ? "无结果" : "成功"));
        return stamp;
    }

    @Override
    public int insertStamp(Stamp stamp) {
        log.info("正在插入stamp");
        if (super.save(stamp)) {
            log.info("插入stamp成功,id为{}", stamp.getStampId());
            return stamp.getStampId();
        } else {
            log.error("插入stamp失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteStampById(int id) {
        log.info("正在删除id为{}的stamp", id);
        if (super.removeById(id)) {
            log.info("删除id为{}的stamp成功", id);
            return id;
        } else {
            log.error("删除id为{}的stamp失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateStamp(Stamp stamp) {
        log.info("正在更新id为{}的stamp", stamp.getStampId());
        if (super.updateById(stamp)) {
            log.info("更新d为{}的stamp成功", stamp.getStampId());
            return stamp.getStampId();
        } else {
            log.error("更新id为{}的stamp失败", stamp.getStampId());
            throw new BizException("更新失败[id=" + stamp.getStampId() + "]");
        }
    }

}
