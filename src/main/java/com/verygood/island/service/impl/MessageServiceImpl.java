package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Message;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.MessageMapper;
import com.verygood.island.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 树洞留言 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public Page<Message> listMessagesByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询message: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<Message>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Message> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询message完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public Message getMessageById(int id) {
        log.info("正在查询message中id为{}的数据", id);
        Message message = super.getById(id);
        log.info("查询id为{}的message{}", id, (null == message ? "无结果" : "成功"));
        return message;
    }

    @Override
    public int insertMessage(Message message) {
        log.info("正在插入message");
        message.setTime(LocalDateTime.now());
        if (super.save(message)) {
            log.info("插入message成功,id为{}", message.getMessageId());
            return message.getMessageId();
        } else {
            log.error("插入message失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteMessageById(int id) {
        log.info("正在删除id为{}的message", id);
        if (super.removeById(id)) {
            log.info("删除id为{}的message成功", id);
            return id;
        } else {
            log.error("删除id为{}的message失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateMessage(Message message) {
        log.info("正在更新id为{}的message", message.getMessageId());
        if (super.updateById(message)) {
            log.info("更新d为{}的message成功", message.getMessageId());
            return message.getMessageId();
        } else {
            log.error("更新id为{}的message失败", message.getMessageId());
            throw new BizException("更新失败[id=" + message.getMessageId() + "]");
        }
    }

}
