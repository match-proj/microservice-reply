package com.match.reply.context.domain.service;

import com.match.common.PageResult;
import com.match.reply.client.bean.ReplyPublishDto;
import com.match.reply.client.bean.ReplySimpleDto;
import com.match.reply.client.bean.ReplyType;

/**
 * @Author zhangchao
 * @Date 2019/5/28 10:04
 * @Version v1.0
 */
public interface ReplyService {
    void publish(String userId, ReplyPublishDto publishDto);

    PageResult<ReplySimpleDto> list(Integer page, Integer size, ReplyType replyType, String resourceId);

    Integer countByResourceIdAndCommentsType(String resourceId, ReplyType replyType);
}
