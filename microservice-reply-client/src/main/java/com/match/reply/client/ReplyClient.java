package com.match.reply.client;

import com.match.common.PageResult;
import com.match.common.feign.fallback.HystrixClientFallbackFactory;
import com.match.reply.client.bean.ReplyPublishDto;
import com.match.reply.client.bean.ReplySimpleDto;
import com.match.reply.client.bean.ReplyType;
import com.match.reply.client.configuration.FeignSupportConfig;
import com.match.reply.client.fallback.FallbackReplyClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/8/2 10:09
 * @Version v1.0
 */
@FeignClient(name = "microservice-reply",configuration = FeignSupportConfig.class, fallback = FallbackReplyClient.class)
public interface ReplyClient {

    @PostMapping("/reply/publish")
    void publish(@Valid @RequestBody ReplyPublishDto publishDto);


    @GetMapping("/reply/list")
    PageResult<ReplySimpleDto> list(@RequestParam(required = false, name = "page", defaultValue = "1") Integer page,
                                           @RequestParam(required = false, name = "size", defaultValue = "10") Integer size,
                                           @RequestParam(name = "replyType") ReplyType replyType,
                                           @RequestParam(name = "resourceId") String resourceId
    );

    @GetMapping("/reply/countByResourceIdAndCommentsType")
    Integer countByResourceIdAndCommentsType(@RequestParam(name = "resourceId")String resourceId, @RequestParam(name = "replyType")ReplyType replyType);
}
