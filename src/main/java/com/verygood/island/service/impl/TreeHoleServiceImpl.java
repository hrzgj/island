package com.verygood.island.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.TreeHole;
import com.verygood.island.entity.vo.TreeHoleVo;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.MessageMapper;
import com.verygood.island.mapper.TreeHoleMapper;
import com.verygood.island.service.TreeHoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    TreeHoleMapper treeHoleMapper;

    @Resource
    MessageMapper messageMapper;

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
    public TreeHoleVo getTreeHoleById(int id) {
        log.info("正在查询treeHole中id为{}的数据", id);
        TreeHole treeHole = super.getById(id);
        TreeHoleVo treeHoleVo=new TreeHoleVo();
        BeanUtil.copyProperties(treeHole,treeHoleVo);
        Map<String,Object> map=new HashMap<>();
        map.put("tree_hole_id",id);
        treeHoleVo.setMessages(messageMapper.selectByMap(map));
        log.info("查询id为{}的treeHole{}", id, (null == treeHole ? "无结果" : "成功"));
        return treeHoleVo;
    }

    @Override
    public int insertTreeHole(TreeHole treeHole) {
        log.info("正在插入treeHole");
        treeHole.setCreateTime(LocalDateTime.now());
        Integer userTreeNumber=treeHoleMapper.getUserTreeNumber(treeHole.getCreatorId());
        if(userTreeNumber>=5){
            log.error("插入treeHole失败，用户树洞超过五个");
            throw new BizException("用户树洞已有5个，无法添加");
        }
        if (super.save(treeHole)) {
            log.info("插入treeHole成功,id为{}", treeHole.getTreeHoleId());
            return treeHole.getTreeHoleId();
        } else {
            log.error("插入treeHole失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteTreeHoleById(int id,Integer userId) {
        log.info("正在删除id为{}的treeHole", id);
        if(!userId.equals(treeHoleMapper.getUserIdByTreeId(id))){
            log.error("更新id为{}的treeHole失败,登录用户与树洞拥有用户不同",id);
            throw new BizException("登录用户与树洞拥有用户不同");
        }
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
        Integer userId=treeHoleMapper.getUserIdByTreeId(treeHole.getTreeHoleId());
        if(!userId.equals(treeHole.getCreatorId())){
            log.error("更新id为{}的treeHole失败,登录用户与树洞拥有用户不同", treeHole.getTreeHoleId());
            throw new BizException("登录用户与树洞拥有用户不同");
        }
        if (super.updateById(treeHole)) {
            log.info("更新d为{}的treeHole成功", treeHole.getTreeHoleId());
            return treeHole.getTreeHoleId();
        } else {
            log.error("更新id为{}的treeHole失败", treeHole.getTreeHoleId());
            throw new BizException("更新失败[id=" + treeHole.getTreeHoleId() + "]");
        }
    }

}
