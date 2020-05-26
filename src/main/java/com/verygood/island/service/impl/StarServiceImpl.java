package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Star;
import com.verygood.island.entity.User;
import com.verygood.island.entity.vo.UserVo;
import com.verygood.island.entity.User;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.StarMapper;
import com.verygood.island.mapper.UserMapper;
import com.verygood.island.service.StarService;
import com.verygood.island.util.LocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LocationUtils locationUtils;

    @Override
    public Page<UserVo> listStarsByPage(int page, int pageSize, String factor, Integer userId) {
        log.info("正在执行分页查询star: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<Star> queryWrapper = new QueryWrapper<Star>().eq("user_id", userId);
        Page<Star> result = super.page(new Page<>(page, pageSize), queryWrapper);
        log.info("分页查询star完毕: 结果数 = {} ", result.getRecords().size());

        log.info("正在查询star对应的user");

        List<UserVo> userVoList = new LinkedList<>();
        for (Star star : result.getRecords()) {
            User user = userMapper.selectById(star.getIslandId());
            // 查看自己的信息
            User self = (User) SecurityUtils.getSubject().getPrincipal();
            //转成vo
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            userVo.setDistance(locationUtils.getDistance(user.getCity(), self.getCity()));
            userVoList.add(userVo);
        }
        Page<UserVo> userVoPage = new Page<>();
        BeanUtils.copyProperties(result, userVoPage);
        userVoPage.setRecords(userVoList);
        return userVoPage;
    }

    @Override
    public Star getStarById(int id) {
        log.info("正在查询star中id为{}的数据", id);
        Star star = super.getById(id);
        log.info("查询id为{}的star{}", id, (null == star ? "无结果" : "成功"));
        return star;
    }

    @Override
    public int insertStar(Star star, Integer userId) {
        log.info("正在插入star");

        // 查询海岛id是否为空
        if (star == null || star.getIslandId() == null){
            log.info("插入star时参数不足");
            throw new BizException("海岛ID不能为空！");
        }

        // 查看海岛是否存在
        if (userMapper.selectCount(new QueryWrapper<User>()
        .eq("user_id", star.getIslandId())) == 0){
            log.info("星标海岛时id为【{}】的海岛不存在", star.getIslandId());
            throw new BizException("星标的海岛不存在！");
        }

        // 查看是否星标的是自己的海岛
        if (star.getIslandId().equals(userId)){
            log.info("星标海岛时用户【{}】尝试星标自己的海岛", userId);
            throw new BizException("不可以星标自己的海岛");
        }

        // 查看自己是否已经星标了该海岛
        if (super.count(new QueryWrapper<Star>()
        .eq("user_id", userId)
        .eq("island_id", star.getIslandId())) > 0){
            log.info("星标海岛时用户【{}】已星标了【{}】海岛", userId, star.getIslandId());
            throw new BizException("您已经星标了该海岛");
        }

        // 进行星标操作
        star.setUserId(userId);

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
