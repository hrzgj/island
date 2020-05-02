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
 * 回复
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Reply extends Model<Reply> {

    private static final long serialVersionUID = 1L;

    /**
     * 回复id
     */
    @TableId(value = "reply_id", type = IdType.AUTO)
    private Integer replyId;

    /**
     * 回复者id
     */
    private Integer writerId;

    /**
     * 被回复的回复id
     */
    private Integer beReplyId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 帖子id
     */
    private Integer postId;

    /**
     * 回复时间
     */
    private LocalDateTime time;


    @Override
    protected Serializable pkVal() {
        return this.replyId;
    }

}
