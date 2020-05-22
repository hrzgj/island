package com.verygood.island.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 树洞
 *
 * </p>
 *
 * @author chaos
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TreeHole extends Model<TreeHole> {

    private static final long serialVersionUID = 1L;

    /**
     * 树洞id
     */
    @TableId(value = "tree_hole_id", type = IdType.AUTO)
    private Integer treeHoleId;

    /**
     * 创建者id
     */
    private Integer creatorId;

    /**
     * 树洞内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 树洞名称
     */
    private String title;



    @Override
    protected Serializable pkVal() {
        return this.treeHoleId;
    }

}
