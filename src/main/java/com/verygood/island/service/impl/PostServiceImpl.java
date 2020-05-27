package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Post;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.PostMapper;
import com.verygood.island.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 海岛动态 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Page<Post> listPostsByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询post: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<Post> queryWrapper = new QueryWrapper<Post>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Post> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询post完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public Post getPostById(int id) {
        log.info("正在查询post中id为{}的数据", id);
        Post post = super.getById(id);
        if (post == null) {
            log.info("没有id为{}的post", id);
            throw new BizException("没有该动态");
        }
        log.info("查询id为{}的post{}", id, "成功");
        post.setView(post.getView() + 1);
        super.updateById(post);
        return post;
    }

    @Override
    public int insertPost(Post post) {
        log.info("正在插入post");
        post.setView(0);
        post.setTime(LocalDateTime.now());
        if (super.save(post)) {
            log.info("插入post成功,id为{}", post.getPostId());
            return post.getPostId();
        } else {
            log.error("插入post失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deletePostById(int id, Integer userId) {
        log.info("正在删除id为{}的post", id);

        // 查看权限
        Post post = super.getById(id);

        if (post == null){
            log.info("执行删除海岛动态时传输了错误的id【{}】， 该id找不到对应的海岛动态", id);
            throw new BizException("请校验对应的海岛动态id");
        }

        if (!post.getUserId().equals(userId)){
            log.info("执行删除海岛动态时，删除人【{}】并非海岛动态属于者【{}】", userId, post.getUserId());
            throw new BizException("删除海岛动态失败！您并非该海岛动态的发送者");
        }

        // 执行删除操作
        if (super.removeById(id)) {
            log.info("删除id为{}的post成功", id);
            return id;
        } else {
            log.error("删除id为{}的post失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updatePost(Post post) {
        log.info("正在更新id为{}的post", post.getPostId());
        if (super.updateById(post)) {
            log.info("更新d为{}的post成功", post.getPostId());
            return post.getPostId();
        } else {
            log.error("更新id为{}的post失败", post.getPostId());
            throw new BizException("更新失败[id=" + post.getPostId() + "]");
        }
    }

    @Override
    public List<Post> getByUserId(Integer id) {
        log.info("正在查询userId为{}的post列表数据", id);
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", id);
        List<Post> posts = super.listByMap(map);
        if (posts == null) {
            log.info("userId为{}的没有post", id);
            return null;
        }
        return posts;
    }

}
