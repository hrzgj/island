package com.verygood.island.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verygood.island.entity.Letter;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.mapper.LetterMapper;
import com.verygood.island.service.LetterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<Letter> listLettersByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询letter: page = {} pageSize = {} factor = {}", page, pageSize, factor);
        QueryWrapper<Letter> queryWrapper = new QueryWrapper<Letter>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<Letter> result = super.page(new Page<>(page, pageSize));
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
        if (super.save(letter)) {
            log.info("插入letter成功,id为{}", letter.getLetterId());
            return letter.getLetterId();
        } else {
            log.error("插入letter失败");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteLetterById(int id) {
        log.info("正在删除id为{}的letter", id);
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
        if (super.updateById(letter)) {
            log.info("更新d为{}的letter成功", letter.getLetterId());
            return letter.getLetterId();
        } else {
            log.error("更新id为{}的letter失败", letter.getLetterId());
            throw new BizException("更新失败[id=" + letter.getLetterId() + "]");
        }
    }

}
