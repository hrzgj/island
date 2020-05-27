package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Notice;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.NoticeMapper;
import com.verygood.island.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通知 服务实现类
 * </p>
 *
 * @author chaos
 * @since 2020-05-04
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public List<Notice> listNoticesByPage(Integer userId) {
        log.info("正在返回userId={}的通知", userId);
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().eq("user_id", userId).eq("is_read", 0);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        List<Notice> notices = super.list(queryWrapper);
        log.info("返回通知数量{}", notices.size());
        return notices;
    }

    @Override
    public Notice getNoticeById(int id) {
        log.info("正在查询notice中id为{}的数据", id);
        Notice notice = super.getById(id);
        log.info("查询id为{}的notice{}", id, (null == notice ? "无结果" : "成功"));
        return notice;
    }

    @Override
    public int insertNotice(Notice notice) {
        log.info("正在插入notice");
        if (super.save(notice)) {
            log.info("插入notice成功,id为{}", notice.getNoticeId());
            return notice.getNoticeId();
        } else {
            log.error("插入notice失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteNoticeById(int id) {
        log.info("正在删除id为{}的notice", id);
        if (super.removeById(id)) {
            log.info("删除id为{}的notice成功", id);
            return id;
        } else {
            log.error("删除id为{}的notice失败", id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateNotice(Notice notice) {
        log.info("正在更新id为{}的notice", notice.getNoticeId());
        if (super.updateById(notice)) {
            log.info("更新d为{}的notice成功", notice.getNoticeId());
            return notice.getNoticeId();
        } else {
            log.error("更新id为{}的notice失败", notice.getNoticeId());
            throw new BizException("更新失败[id=" + notice.getNoticeId() + "]");
        }
    }

    @Override
    public int readNoticeById(Integer userId, Integer noticeId) {
        log.info("正在更新id为{}的notice的读取状态", noticeId);
        if (noticeId == null) {
            log.info("id为空，无法进行更新读状态");
            throw new BizException("更新消息状态失败！请校验参数");
        }

        // 查询对应的notice记录
        Notice notice = getById(noticeId);

        if (notice == null) {
            log.info("查询id为【{}】的notice，notice不存在！", noticeId);
            throw new BizException("更新消息状态失败！不存在该消息");
        }

        // 设置为已读状态
        if (notice.getIsRead()) {
            log.info("更新id为【{}】的notice读状态失败！已经被读", noticeId);
            throw new BizException("该条消息已经被读，不可重复读取");
        }

        notice.setIsRead(Boolean.TRUE);
        // 执行更新操作
        if (super.updateById(notice)) {
            log.info("更新d为{}的notice成功", notice.getNoticeId());
            return notice.getNoticeId();
        } else {
            log.error("更新id为{}的notice失败", notice.getNoticeId());
            throw new BizException("更新失败[id=" + notice.getNoticeId() + "]");
        }
    }
}
