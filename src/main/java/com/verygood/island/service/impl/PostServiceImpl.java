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

/**
* <p>
* 海岛动态 服务实现类
* </p>
*
* @author chaos
* @since 2020-05-02
*/
@Slf4j
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Page<Post> listPostsByPage(int page, int pageSize, String keyword) {
        log.info("正在执行分页查询post: page = {} pageSize = {} keyword = {}",page,pageSize,keyword);
        QueryWrapper<Post> queryWrapper =  new QueryWrapper<Post>().like("", keyword);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Post> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询post完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public Post getPostById(int id) {
        log.info("正在查询post中id为{}的数据",id);
        Post post = super.getById(id);
        log.info("查询id为{}的post{}",id,(null == post?"无结果":"成功"));
        return post;
    }

    @Override
    public int insertPost(Post post) {
        log.info("正在插入post");
        if (super.save(post)) {
            log.info("插入post成功,id为{}",post.getPostId());
            return post.getPostId();
        } else {
            log.error("插入post失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deletePostById(int id) {
        log.info("正在删除id为{}的post",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的post成功",id);
            return id;
        } else {
            log.error("删除id为{}的post失败",id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updatePost(Post post) {
        log.info("正在更新id为{}的post",post.getPostId());
        if (super.updateById(post)) {
            log.info("更新d为{}的post成功",post.getPostId());
            return post.getPostId();
        } else {
            log.error("更新id为{}的post失败",post.getPostId());
            throw new BizException("更新失败[id=" + post.getPostId() + "]");
        }
    }

}
