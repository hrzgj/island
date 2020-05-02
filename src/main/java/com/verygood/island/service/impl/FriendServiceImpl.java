package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Friend;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.FriendMapper;
import com.verygood.island.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* <p>
* 笔友 服务实现类
* </p>
*
* @author chaos
* @since 2020-05-02
*/
@Slf4j
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Override
    public Page<Friend> listFriendsByPage(int page, int pageSize, String keyword) {
        log.info("正在执行分页查询friend: page = {} pageSize = {} keyword = {}",page,pageSize,keyword);
        QueryWrapper<Friend> queryWrapper =  new QueryWrapper<Friend>().like("", keyword);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Friend> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询friend完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public Friend getFriendById(int id) {
        log.info("正在查询friend中id为{}的数据",id);
        Friend friend = super.getById(id);
        log.info("查询id为{}的friend{}",id,(null == friend?"无结果":"成功"));
        return friend;
    }

    @Override
    public int insertFriend(Friend friend) {
        log.info("正在插入friend");
        if (super.save(friend)) {
            log.info("插入friend成功,id为{}",friend.getFriendId());
            return friend.getFriendId();
        } else {
            log.error("插入friend失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteFriendById(int id) {
        log.info("正在删除id为{}的friend",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的friend成功",id);
            return id;
        } else {
            log.error("删除id为{}的friend失败",id);
            throw new BizException("删除失败[id=" + id + "]");
        }
    }

    @Override
    public int updateFriend(Friend friend) {
        log.info("正在更新id为{}的friend",friend.getFriendId());
        if (super.updateById(friend)) {
            log.info("更新d为{}的friend成功",friend.getFriendId());
            return friend.getFriendId();
        } else {
            log.error("更新id为{}的friend失败",friend.getFriendId());
            throw new BizException("更新失败[id=" + friend.getFriendId() + "]");
        }
    }

}
