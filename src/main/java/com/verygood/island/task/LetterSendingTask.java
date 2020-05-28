package com.verygood.island.task;

/**
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @description 发信任务
 * @date 2020-05-23 10:20
 */

import com.verygood.island.entity.Letter;
import com.verygood.island.entity.Notice;
import com.verygood.island.entity.Stamp;
import com.verygood.island.entity.User;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.LetterMapper;
import com.verygood.island.mapper.NoticeMapper;
import com.verygood.island.mapper.StampMapper;
import com.verygood.island.mapper.UserMapper;
import com.verygood.island.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 定时发送信件任务
 *
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @date 2020-05-08
 */
@Slf4j
@Transactional
public class LetterSendingTask implements Runnable {

    /**
     * 要定时发送的信件
     */
    private final Letter letter;

    public LetterSendingTask() {
        this.letter = null;
    }

    public LetterSendingTask(Letter letter) {
        this.letter = letter;
    }

    @Override
    public void run() {
        //消耗邮票
        this.useStamp();
        UserMapper userMapper = BeanUtils.getBean(UserMapper.class);
        LetterMapper letterMapper = BeanUtils.getBean(LetterMapper.class);
        //发送信件
        if (null == letter) {
            log.warn("信件为空，无法执行发信任务");
            return;
        }
        letter.setReceiveTime(LocalDateTime.now());
        //统计接收到的信件数量
        User receiver = userMapper.selectById(letter.getReceiverId());
        receiver.setReceiveLetter(receiver.getReceiveLetter() + 1);
        userMapper.updateById(receiver);

        if (letterMapper.updateById(letter) == 1) {
            log.info("发送id为{}的letter成功，接收时间：{}", letter.getLetterId(), letter.getReceiveTime());
            //发送通知
            this.sendNotice();
        } else {
            log.error("发送id为{}的letter失败", letter.getLetterId());
            throw new BizException("发送失败[id=" + letter.getLetterId() + "]");
        }
    }

    /**
     * 使用邮票
     */
    private void useStamp() {
        StampMapper stampMapper = BeanUtils.getBean(StampMapper.class);
        Stamp stamp = new Stamp();
        stamp.setStampId(letter.getStampId());
        stamp.setUserId(letter.getReceiverId());
        stampMapper.updateById(stamp);
        log.info("使用id为{}的邮票成功", stamp.getStampId());
    }

    /**
     * 发送通知
     */
    private void sendNotice() {
        NoticeMapper noticeMapper = BeanUtils.getBean(NoticeMapper.class);
        UserMapper userMapper = BeanUtils.getBean(UserMapper.class);

        User sender = userMapper.selectById(letter.getSenderId());
        Notice notice = new Notice();
        notice.setTitle("收信通知");
        String content = "你收到一封来自" + sender.getNickname() + "的信件，快去查收吧！";
        notice.setContent(content);
        notice.setUserId(letter.getReceiverId());
        noticeMapper.insert(notice);
        log.info("发送notice成功，内容为：{}", content);
    }
}
