package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.constant.Constants;
import com.verygood.island.entity.Letter;
import com.verygood.island.entity.Stamp;
import com.verygood.island.entity.User;
import com.verygood.island.entity.vo.LetterVo;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.LetterMapper;
import com.verygood.island.mapper.StampMapper;
import com.verygood.island.mapper.UserMapper;
import com.verygood.island.service.LetterService;
import com.verygood.island.task.CapsuleSendingTask;
import com.verygood.island.task.LetterSendingTask;
import com.verygood.island.util.LocationUtils;
import com.verygood.island.util.ScheduledUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocationUtils locationUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StampMapper stampMapper;

    @Autowired
    private ScheduledUtils scheduledUtils;


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

        // 校验信件
        checkLetter(letter);

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
        //不允许更新接收时间
        letter.setReceiveTime(null);

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

        //计算收信时间
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

        //消耗邮票
        if (letter.getSenderId() != null) {
            Stamp stamp = stampMapper.selectById(letter.getStampId());
            if (stamp == null || stamp.getUserId() == null || !stamp.getUserId().equals(sender.getUserId())) {
                log.warn("id为{}的信件没有使用有效邮票，无法发信", letter.getLetterId());
                throw new BizException("发信失败，缺少有效的邮票");
            }
        } else {
            log.warn("id为{}的信件没有使用邮票", letter.getLetterId());
            throw new BizException("请选择一张邮票进行发信!");
        }

        UpdateWrapper<Stamp> stampUpdateWrapper = new UpdateWrapper<>();
        stampUpdateWrapper.eq("stamp_id", letter.getStampId())
                //设置为null,既不属于发信人也不属于收信人
                .set("user_id", null);
        stampMapper.update(new Stamp(), stampUpdateWrapper);

        //统计书写字数
        if (letter.getContent() == null || letter.getContent().trim().isEmpty()) {
            log.warn("不允许插入空的信件");
            throw new BizException("无法发送空的信件");
        }
        sender.setWord(sender.getWord() + letter.getContent().trim().length());
        //统计写过的信件数量
        sender.setSendLetter(sender.getSendLetter() + 1);
        userMapper.updateById(sender);


        //启动定时任务
        log.info("正在启动定时任务");
        scheduledUtils.addTask(calculateReceiveTime(distance), new LetterSendingTask(letter));
    }


    @Override
    public List<LetterVo> getOneFriendLetter(Integer friendId, Integer userId) {
        //得到互送的信件
        log.info("正在执行信件查询letter: friendId = {} userId = {}", friendId, userId);
        QueryWrapper<Letter> queryWrapper = new QueryWrapper<Letter>().eq("sender_id", friendId).eq("receiver_id", userId)
                .or().eq("receiver_id", friendId).eq("sender_id", userId);

        List<Letter> letters = super.list(queryWrapper);
        List<LetterVo> letterVos = new ArrayList<>(letters.size());
        for (Letter letter : letters) {
            LetterVo letterVo = new LetterVo();
            letterVo.setLetter(letter);
            letterVo.setNickname(userMapper.getNicknameByUserId(letterVo.getLetter().getSenderId()));
            letterVo.setStampName(stampMapper.getStampNameByStampId(letterVo.getLetter().getStampId()));
            letterVos.add(letterVo);
        }
        return letterVos;
    }

    /**
     * 查询草稿箱列表
     *
     * @param userId 用户id
     * @return : java.util.List<com.verygood.island.entity.Letter>
     * @author : huange7
     * @date : 2020-05-14 21:12
     */
    @Override
    public List<Letter> getLetterDraft(Integer userId) {
        log.info("开始执行查询用户【{}】的草稿列表：", userId);
        return super.list(new QueryWrapper<Letter>().eq("sender_id", userId)
                // 未发送的信件，即为草稿
                .eq("is_send", false)
                // 接收方不是自己，即排除时间胶囊
                .ne("receiver_id", userId));

    }

    @Override
    public int sendCapsuleLetter(Letter letter, Integer userId) {
        log.info("开始执行发送时间胶囊：【{}】", letter);

        if (StringUtils.isEmpty(letter.getContent()) || letter.getReceiveTime() == null) {
            log.info("发送时间胶囊时内容为空或者接收时间为空！");
            throw new BizException("时间胶囊内容或者胶囊的接收时间不应为空");
        }

        // 校验信件
        checkLetter(letter);

        // 查看发送时间是否在当前时间之前
        if (letter.getReceiveTime().isBefore(LocalDateTime.now())) {
            log.info("发送时间胶囊时检测到接收时间为当前时间之前");
            throw new BizException("接收时间不可以在当前时间之前");
        }

        User user = userMapper.selectById(userId);

        if (user == null) {
            throw new BizException("不存在该用户信息");
        }

        // 检测剩余的时间胶囊数量
        if (user.getCapsule() <= 0) {
            log.info("发送时间胶囊时检测到时间胶囊数不足");
            throw new BizException("时间胶囊数量不足，无法发送时间胶囊");
        }

        int taskCount = super.count(new QueryWrapper<Letter>()
                .eq("sender_id", userId)
                .eq("receiver_id", userId)
                .isNull("receive_time"));

        if (taskCount >= user.getCapsule()) {
            log.info("发送时间胶囊时检测到定时任务数量已经达到可以消耗的时间胶囊数量上限");
            throw new BizException("当前的定时任务数量已经到达上限！");
        }

        // 设置相关属性
        letter.setSenderId(userId);
        letter.setReceiverId(userId);
        letter.setIsSend(true);
        letter.setStampId(0);
        letter.setSendTime(LocalDateTime.now());

        if (super.save(letter)) {
            log.info("插入letter成功,id为{}", letter.getLetterId());
            //发送信件
            scheduledUtils.addTask(letter.getReceiveTime(), new CapsuleSendingTask(letter));
            return letter.getLetterId();
        } else {
            log.error("插入letter失败");
            throw new BizException("保存失败");
        }
    }

    /**
     * 校验信件内容
     *
     * @param letter
     */
    private void checkLetter(Letter letter) {
        // 校验信件标题
        if (StringUtils.isEmpty(letter.getHeader()) || letter.getHeader().length() > Constants.LETTER_HEADER_MAX_LENGTH) {
            log.info("发送信件时信件标题为空或者信件标题长度过长");
            throw new BizException("信件标题不应为空并且长度不应超过10个字");
        }

        // 校验信件内容
        if (StringUtils.isEmpty(letter.getContent()) || letter.getContent().length() > Constants.LETTER_CONTENT_MAX_LENGTH) {
            log.info("发送信件时信件内容为空或者信件内容长度过长");
            throw new BizException("信件内容不应为空并且长度不应超过60000个字");
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
    private LocalDateTime calculateReceiveTime(long distance) {
        //每小时经过的距离
        int milePerHour = 417660;
        int hour = (int) (distance / milePerHour);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime receiveTime = null;
        if (hour == 0) {
            //最少需要1分钟
            receiveTime = now.plusMinutes(1);
        } else {
            receiveTime = now.plusHours(hour);
        }
        log.info("预计收信时间: {}", receiveTime);
        return receiveTime;
    }

}
