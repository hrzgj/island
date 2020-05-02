package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Notice;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.NoticeMapper;
import com.verygood.island.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* <p>
* 通知 服务实现类
* </p>
*
* @author chaos
* @since 2020-05-02
*/
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public Page<Notice> listNoticesByPage(int page, int pageSize, String keyword) {
        log.info("正在执行分页查询notice: page = {} pageSize = {} keyword = {}",page,pageSize,keyword);
        QueryWrapper<Notice> queryWrapper =  new QueryWrapper<Notice>().like("", keyword);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Notice> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询notice完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public Notice getNoticeById(int id) {
        log.info("正在查询notice中id为{}的数据",id);
        Notice notice = super.getById(id);
        log.info("查询id为{}的notice{}",id,(null == notice?"无结果":"成功"));
        return notice;
    }

    @Override
    public int insertNotice(Notice notice) {
        log.info("正在插入notice");
        if (super.save(notice)) {
            log.info("插入notice成功,id为{}",notice.getNoticeId());
            return notice.getNoticeId();
        } else {
            log.error("插入notice失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteNoticeById(int id) {
        log.info("正在删除id为{}的notice",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的notice成功",id);
            return id;
        } else {
            log.error("删除id为{}的notice失败",id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateNotice(Notice notice) {
        log.info("正在更新id为{}的notice",notice.getNoticeId());
        if (super.updateById(notice)) {
            log.info("更新d为{}的notice成功",notice.getNoticeId());
            return notice.getNoticeId();
        } else {
            log.error("更新id为{}的notice失败",notice.getNoticeId());
            throw new BizException("更新失败[id=" + notice.getNoticeId() + "]");
        }
    }

}
