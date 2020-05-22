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
 * 用户
 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 用户写过的字数
     */
    private Integer word;

    /**
     * 头像
     */
    private String photo;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 签名
     */
    private String signature;

    /**
     * 海岛背景
     */
    private String background;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 胶囊数量
     */
    private Integer capsule;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
