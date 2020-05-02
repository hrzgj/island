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
 * 树洞留言
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    /**
     * 树洞留言id
     */
    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer messageId;

    /**
     * 留言者id
     */
    private Integer writerId;

    /**
     * 内容
     */
    private String content;

    /**
     * 树洞id
     */
    private Integer treeHoleId;

    /**
     * 留言时间
     */
    private LocalDateTime time;


    @Override
    protected Serializable pkVal() {
        return this.messageId;
    }

}
