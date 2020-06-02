package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Reply;
import com.verygood.island.entity.User;
import com.verygood.island.entity.vo.ReplyVo;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.ReplyMapper;
import com.verygood.island.mapper.UserMapper;
import com.verygood.island.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回复 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Reply> listReplysByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询reply: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<Reply>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Reply> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询reply完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public ReplyVo getReplyById(int id) {
        log.info("正在查询reply中id为{}的数据", id);
        Reply reply = super.getById(id);
        log.info("查询id为{}的reply{}", id, (null == reply ? "无结果" : "成功"));
        if(reply==null){
            return null;
        }
        ReplyVo replyVo=new ReplyVo();
        replyVo.setReply(reply);
        //设置回复用户头像和昵称
        if(reply.getWriterId()!=null) {
            replyVo.setReplyName(userMapper.getNicknameByUserId(reply.getWriterId()));
            replyVo.setReplyPhoto(userMapper.getPhotoById(reply.getWriterId()));
        }
        //设置被回复用户头像和昵称
        if(reply.getBeReplyId()!=null) {
            replyVo.setBeReplyPhoto(userMapper.getPhotoById(reply.getBeReplyId()));
            replyVo.setBeReplyName(userMapper.getNicknameByUserId(reply.getBeReplyId()));
        }
        return replyVo;
    }

    @Override
    public int insertReply(Reply reply) {
        log.info("正在插入reply");
        reply.setTime(LocalDateTime.now());
        if (super.save(reply)) {
            log.info("插入reply成功,id为{}", reply.getReplyId());
            return reply.getReplyId();
        } else {
            log.error("插入reply失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteReplyById(int id) {
        log.info("正在删除id为{}的reply", id);
        if (super.removeById(id)) {
            log.info("删除id为{}的reply成功", id);
            return id;
        } else {
            log.error("删除id为{}的reply失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateReply(Reply reply) {
        log.info("正在更新id为{}的reply", reply.getReplyId());
        if (super.updateById(reply)) {
            log.info("更新d为{}的reply成功", reply.getReplyId());
            return reply.getReplyId();
        } else {
            log.error("更新id为{}的reply失败", reply.getReplyId());
            throw new BizException("更新失败[id=" + reply.getReplyId() + "]");
        }
    }

    @Override
    public List<ReplyVo> getByPostId(Integer id) {
        log.info("正在查询reply中postId为{}的数据", id);
        Map<String, Object> map = new HashMap<>();
        map.put("post_id", id);
        List<Reply> replays = super.listByMap(map);
        List<ReplyVo> replyVos=new ArrayList<>(replays.size());
        for(Reply reply:replays){
            ReplyVo replyVo=new ReplyVo();
            //设置被回复用户头像和昵称
            if(reply.getBeReplyId()!=null) {
                replyVo.setBeReplyPhoto(userMapper.getPhotoById(reply.getBeReplyId()));
                replyVo.setBeReplyName(userMapper.getNicknameByUserId(reply.getBeReplyId()));
            }
            replyVo.setReply(reply);
            if(reply.getWriterId()!=null) {
                replyVo.setReplyName(userMapper.getNicknameByUserId(reply.getWriterId()));
                replyVo.setReplyPhoto(userMapper.getPhotoById(reply.getWriterId()));
            }
            replyVos.add(replyVo);
        }
        log.info("postId为{}查询reply完毕: 结果数 = {} ", id, replays.size());
        return replyVos;
    }

}
