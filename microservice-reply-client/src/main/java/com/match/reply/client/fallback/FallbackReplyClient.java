package com.match.reply.client.fallback;

import com.match.common.PageResult;
import com.match.common.utils.JsonUtils;
import com.match.reply.client.ReplyClient;
import com.match.reply.client.bean.ReplyPublishDto;
import com.match.reply.client.bean.ReplySimpleDto;
import com.match.reply.client.bean.ReplyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/8/2 17:36
 * @Version v1.0
 */
@Slf4j
@Component
public class FallbackReplyClient implements ReplyClient {
    @Override
    public void publish(@Valid ReplyPublishDto publishDto) {
        log.error("publish:{}", JsonUtils.obj2json(publishDto));
    }

    @Override
    public PageResult<ReplySimpleDto> list(Integer page, Integer size, ReplyType replyType, String resourceId) {
        log.error("list({},{},{},{})",page,size,replyType,resourceId);
        return new PageResult<>();
    }

    @Override
    public Integer countByResourceIdAndCommentsType(String resourceId, ReplyType replyType) {
        log.error("countByResourceIdAndCommentsType({},{})",resourceId,replyType);
        return 0;
    }
}
