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
 * 星标

 * </p>
 *
 * @author chaos
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Star extends Model<Star> {

    private static final long serialVersionUID = 1L;

    /**
     * 星标id
     */
    @TableId(value = "star_id", type = IdType.AUTO)
    private Integer starId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 海岛用户id
     */
    private Integer islandId;


    @Override
    protected Serializable pkVal() {
        return this.starId;
    }

}
