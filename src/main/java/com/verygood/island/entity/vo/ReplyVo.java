package com.verygood.island.entity.vo;

import com.verygood.island.entity.Reply;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: chenyu
 * @date: 2020/5/28 10:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReplyVo {
    Reply reply;
    String replyPhoto;
    String beReplyPhoto;
}
