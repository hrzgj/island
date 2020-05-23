package com.verygood.island.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通知
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Notice extends Model<Notice> {

    private static final long serialVersionUID = 1L;

    /**
     * 通知id
     */
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 是否已读
     */
    private Boolean isRead;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 发送时间
     */
    private LocalDateTime time;


    @Override
    protected Serializable pkVal() {
        return this.noticeId;
    }

}
