package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Reply;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.ReplyMapper;
import com.verygood.island.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* <p>
* 回复 服务实现类
* </p>
*
* @author chaos
* @since 2020-05-02
*/
@Slf4j
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Override
    public Page<Reply> listReplysByPage(int page, int pageSize, String keyword) {
        log.info("正在执行分页查询reply: page = {} pageSize = {} keyword = {}",page,pageSize,keyword);
        QueryWrapper<Reply> queryWrapper =  new QueryWrapper<Reply>().like("", keyword);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Reply> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询reply完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public Reply getReplyById(int id) {
        log.info("正在查询reply中id为{}的数据",id);
        Reply reply = super.getById(id);
        log.info("查询id为{}的reply{}",id,(null == reply?"无结果":"成功"));
        return reply;
    }

    @Override
    public int insertReply(Reply reply) {
        log.info("正在插入reply");
        if (super.save(reply)) {
            log.info("插入reply成功,id为{}",reply.getReplyId());
            return reply.getReplyId();
        } else {
            log.error("插入reply失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteReplyById(int id) {
        log.info("正在删除id为{}的reply",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的reply成功",id);
            return id;
        } else {
            log.error("删除id为{}的reply失败",id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateReply(Reply reply) {
        log.info("正在更新id为{}的reply",reply.getReplyId());
        if (super.updateById(reply)) {
            log.info("更新d为{}的reply成功",reply.getReplyId());
            return reply.getReplyId();
        } else {
            log.error("更新id为{}的reply失败",reply.getReplyId());
            throw new BizException("更新失败[id=" + reply.getReplyId() + "]");
        }
    }

}
