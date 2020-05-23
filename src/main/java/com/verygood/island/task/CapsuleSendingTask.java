package com.verygood.island.task;

import com.verygood.island.entity.Letter;
import com.verygood.island.entity.Notice;
import com.verygood.island.entity.User;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.NoticeMapper;
import com.verygood.island.mapper.UserMapper;
import com.verygood.island.service.impl.LetterServiceImpl;
import com.verygood.island.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Slf4j
public class CapsuleSendingTask implements Runnable {

    /**
     * 存储时间胶囊
     */
    private final Letter letter;

    public CapsuleSendingTask() {
        letter = null;
    }

    /**
     * 构造器
     *
     * @param letter 发送的信件
     */
    public CapsuleSendingTask(Letter letter) {
        this.letter = letter;
    }

    @Override
    public void run() {
        // 时间胶囊数量减1
        reduceCapsule();

        LetterServiceImpl letterService = BeanUtils.getBean(LetterServiceImpl.class);
        letter.setReceiveTime(LocalDateTime.now());

        if (letterService.updateById(letter)) {
            log.info("发送id为{}的letter成功，接收时间：{}", letter.getLetterId(), letter.getReceiveTime());
            //发送通知
            this.sendNotice();
        } else {
            log.error("发送id为{}的letter失败", letter.getLetterId());
            throw new BizException("发送失败[id=" + letter.getLetterId() + "]");
        }
    }

    /**
     * 发送通知
     */
    private void sendNotice() {
        Notice notice = new Notice();
        NoticeMapper noticeMapper = BeanUtils.getBean(NoticeMapper.class);
        notice.setTitle("时间胶囊通知");
        String content = "你收到一个来自" + transferDate(letter.getSendTime()) + "的时间胶囊，快去查收吧！";
        notice.setContent(content);
        notice.setUserId(letter.getReceiverId());
        noticeMapper.insert(notice);
        log.info("发送notice成功，内容为{}", content);
    }

    /**
     * 时间转换工具
     * @param time 将时间转换为中文格式
     * @return 字符串
     */
    private String transferDate(LocalDateTime time){
        return time.getYear() + "年" +
                time.getMonth().getValue() + "月" +
                time.getDayOfMonth() + "日";
    }

    /**
     * 减去对应的时间胶囊
     */
    private void reduceCapsule() {
        UserMapper userMapper = BeanUtils.getBean(UserMapper.class);
        User user = userMapper.selectById(letter.getSenderId());
        if (user == null) {
            log.info("减去时间胶囊时发送错误！不存在该用户");
            return;
        }
        if (user.getCapsule() <= 0) {
            log.info("减去时间胶囊时发送错误！胶囊数量不足");
            return;
        }
        user.setCapsule(user.getCapsule() - 1);
        userMapper.updateById(user);
    }

    @Override
    public String toString() {
        return "CapsuleSendingTask{" +
                "letter=" + letter +
                '}';
    }
}