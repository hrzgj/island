package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Letter;
import com.verygood.island.entity.Notice;
import com.verygood.island.entity.User;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.LetterMapper;
import com.verygood.island.mapper.NoticeMapper;
import com.verygood.island.mapper.UserMapper;
import com.verygood.island.service.LetterService;
import com.verygood.island.util.LocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 信件 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class LetterServiceImpl extends ServiceImpl<LetterMapper, Letter> implements LetterService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private LocationUtils locationUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public Page<Letter> listLettersByPage(int page, int pageSize, Integer friendId, Integer userId) {
        log.info("正在执行分页查询letter: page = {} pageSize = {} friendId = {} userId = {}", page, pageSize, friendId, userId);
        QueryWrapper<Letter> queryWrapper = new QueryWrapper<>();
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        queryWrapper.eq("sender_id", friendId).eq("receiver_id", userId)
                .or().eq("receiver_id", friendId).eq("sender_id", userId);
        Page<Letter> result = super.page(new Page<>(page, pageSize), queryWrapper);
        log.info("分页查询letter完毕: 结果数 = {} ", result.getRecords().size());
        return result;
    }

    @Override
    public Letter getLetterById(int id) {
        log.info("正在查询letter中id为{}的数据", id);
        Letter letter = super.getById(id);
        log.info("查询id为{}的letter{}", id, (null == letter ? "无结果" : "成功"));
        return letter;
    }

    @Override
    public int insertLetter(Letter letter) {
        log.info("正在插入letter");
        //不允许输入信件接收时间
        letter.setReceiveTime(null);

        if (super.save(letter)) {
            log.info("插入letter成功,id为{}", letter.getLetterId());
            //发送信件
            if (letter.getIsSend()) {
                //创建发信任务
                scheduleLetterSending(letter);
            }
            return letter.getLetterId();
        } else {
            log.error("插入letter失败");
            throw new BizException("保存失败");
        }
    }

    @Override
    public int deleteLetterById(int id) {
        log.info("正在删除id为{}的letter", id);
        Letter letter = getById(id);
        //判空
        if (letter == null) {
            log.warn("删除失败，id为{}的信件不存在", id);
            throw new BizException("信件不存在，删除失败");
        }
        //已发出信件不可删除
        if (letter.getIsSend()) {
            log.warn("id为{}的信件已发出，不可删除", id);
            throw new BizException("信件已发出，不可删除");
        }
        //删除草稿
        if (super.removeById(id)) {
            log.info("删除id为{}的letter成功", id);
            return id;
        } else {
            log.error("删除id为{}的letter失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateLetter(Letter letter) {
        log.info("正在更新id为{}的letter", letter.getLetterId());
        Letter letterPo = getById(letter.getLetterId());
        //判空
        if (letterPo == null) {
            log.warn("id为{}的信件不存在，无法更新", letter.getLetterId());
            throw new BizException("信件不存在，无法更新");
        }
        //已发出信件不允许修改
        if (letterPo.getIsSend()) {
            log.warn("id为{}的信件已发出，无法更新", letter.getLetterId());
            throw new BizException("信件已发出，无法更新");
        }

        if (super.updateById(letter)) {
            log.info("更新id为{}的letter成功", letter.getLetterId());
            //发送信件
            if (letter.getIsSend()) {
                //创建发信任务
                scheduleLetterSending(letter);
            }
            return letter.getLetterId();
        } else {
            log.error("更新id为{}的letter失败", letter.getLetterId());
            throw new BizException("更新失败[id=" + letter.getLetterId() + "]");
        }
    }


    /**
     * 创建发信任务
     *
     * @param letter 要发送的信件
     * @name scheduleLetterSending
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-08
     */
    private void scheduleLetterSending(Letter letter) {
        log.info("正在创建发信任务");
        User sender = userMapper.selectById(letter.getSenderId());
        User receiver = userMapper.selectById(letter.getReceiverId());
        if (sender == null || receiver == null) {
            log.error("缺少寄件人或收件人，无法发信");
            throw new BizException("发信失败，缺少寄件人或收件人");
        }
        if (sender.getCity() == null || receiver.getCity() == null) {
            log.error("缺少位置信息，无法发信");
            throw new BizException("发信失败，寄件人或收信人缺少位置信息");
        }
        long distance = locationUtils.getDistance(sender.getCity(), receiver.getCity());
        log.info("计算出两者的距离为：{}米", distance);
        taskScheduler.schedule(new LetterSendingTask(letter, sender), calculateDuration(distance));
    }

    /**
     * 定时发送信件任务
     *
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-08
     */
    private class LetterSendingTask implements Runnable {

        /**
         * 要定时发送的信件
         */
        private final Letter letter;

        /**
         * 写信人
         */
        private final User sender;

        public LetterSendingTask(Letter letter, User sender) {
            this.letter = letter;
            this.sender = sender;
        }

        @Override
        public void run() {
            letter.setReceiveTime(LocalDateTime.now());
            if (updateById(letter)) {
                log.info("发送id为{}的letter成功，接收时间：{}", letter.getLetterId(), letter.getReceiveTime());
                //发送通知
                Notice notice = new Notice();
                notice.setTitle("收信通知");
                String content = "你收到一封来自" + sender.getNickname() + "的信件，快去查收吧！";
                notice.setContent(content);
                notice.setUserId(letter.getReceiverId());
                noticeMapper.insert(notice);
                log.info("发送notice成功，内容为{}", content);
            } else {
                log.error("发送id为{}的letter失败", letter.getLetterId());
                throw new BizException("发送失败[id=" + letter.getLetterId() + "]");
            }
        }
    }


    /**
     * 根据距离计算发信时间
     *
     * @param distance 距离，单位：米
     * @return 返回经过这段距离需要的发信时间
     * @name calculateDuration
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-08
     */
    private Date calculateDuration(long distance) {
        //每小时经过的距离
        int milePerHour = 417660;
        int hour = (int) (distance / milePerHour);
        Date date = new Date();
        if (hour == 0) {
            //最少需要5分钟
            date.setMinutes(date.getMinutes() + 5);
        } else {
            date.setHours(date.getHours() + hour);
        }
        log.info("计算出预计收信时间: {}", date);
        return date;
    }


    @Override
    public List<Letter> getOneFriendLetter(Integer friendId, Integer userId) {
        //得到互送的信件
        log.info("正在执行信件查询letter: friendId = {} userId = {}", friendId, userId);
        QueryWrapper<Letter> queryWrapper = new QueryWrapper<Letter>().eq("sender_id", friendId).eq("receiver_id", userId)
                .or().eq("receiver_id", friendId).eq("sender_id", userId);
        return super.list(queryWrapper);
    }

}
