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
 * 信件
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Letter extends Model<Letter> {

    private static final long serialVersionUID = 1L;

    /**
     * 信件id
     */
    @TableId(value = "letter_id", type = IdType.AUTO)
    private Integer letterId;

    /**
     * 发送者id
     */
    private Integer senderId;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 接收者id
     */
    private Integer receiverId;

    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 信纸
     */
    private String paper;

    /**
     * 是否发送(true=发送，false=保存为草稿)
     */
    private Boolean isSend;

    /**
     * 使用的邮票id
     */
    private Integer stampId;

    /**
     * 信件标题
     */
    private String header;


    @Override
    protected Serializable pkVal() {
        return this.letterId;
    }

}
