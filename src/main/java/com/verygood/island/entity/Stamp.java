package com.verygood.island.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 邮票
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Stamp extends Model<Stamp> {

    private static final long serialVersionUID = 1L;

    /**
     * 邮票id
     */
    @TableId(value = "stamp_id", type = IdType.AUTO)
    private Integer stampId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 邮票名称
     */
    private String stampName;


    @Override
    protected Serializable pkVal() {
        return this.stampId;
    }

}
