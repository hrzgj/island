package com.verygood.island.entity.vo;

import com.verygood.island.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName UserVo
 * @Description 用户类VO
 * @Author huange7
 * @Date 2020-05-21 22:29
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends User {

    /**
     * 距离
     */
    private Long distance;
}
