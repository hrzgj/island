package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.TreeHole;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.TreeHoleMapper;
import com.verygood.island.service.TreeHoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 树洞
 * 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-21
 */
@Slf4j
@Service
public class TreeHoleServiceImpl extends ServiceImpl<TreeHoleMapper, TreeHole> implements TreeHoleService {

    @Override
    public Page<TreeHole> listTreeHolesByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询treeHole: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<TreeHole> queryWrapper = new QueryWrapper<TreeHole>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<TreeHole> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询treeHole完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public TreeHole getTreeHoleById(int id) {
        log.info("正在查询treeHole中id为{}的数据", id);
        TreeHole treeHole = super.getById(id);
        log.info("查询id为{}的treeHole{}", id, (null == treeHole ? "无结果" : "成功"));
        return treeHole;
    }

    @Override
    public int insertTreeHole(TreeHole treeHole) {
        log.info("正在插入treeHole");
        if (super.save(treeHole)) {
            log.info("插入treeHole成功,id为{}", treeHole.getTreeHoleId());
            return treeHole.getTreeHoleId();
        } else {
            log.error("插入treeHole失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteTreeHoleById(int id) {
        log.info("正在删除id为{}的treeHole", id);
        if (super.removeById(id)) {
            log.info("删除id为{}的treeHole成功", id);
            return id;
        } else {
            log.error("删除id为{}的treeHole失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateTreeHole(TreeHole treeHole) {
        log.info("正在更新id为{}的treeHole", treeHole.getTreeHoleId());
        if (super.updateById(treeHole)) {
            log.info("更新d为{}的treeHole成功", treeHole.getTreeHoleId());
            return treeHole.getTreeHoleId();
        } else {
            log.error("更新id为{}的treeHole失败", treeHole.getTreeHoleId());
            throw new BizException("更新失败[id=" + treeHole.getTreeHoleId() + "]");
        }
    }

}
