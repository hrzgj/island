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
 * 笔友
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Friend extends Model<Friend> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "friend_id", type = IdType.AUTO)
    private Integer friendId;

    /**
     * 笔友的用户id
     */
    private Integer friendUserId;

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * 笔友备注
     */
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.friendId;
    }

}
