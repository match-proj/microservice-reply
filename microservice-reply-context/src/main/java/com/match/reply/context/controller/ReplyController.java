package com.match.reply.context.controller;

import com.match.common.PageResult;
import com.match.common.context.UserContext;
import com.match.reply.client.ReplyClient;
import com.match.reply.client.bean.ReplyPublishDto;
import com.match.reply.client.bean.ReplySimpleDto;
import com.match.reply.client.bean.ReplyType;
import com.match.reply.context.domain.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/5/28 10:05
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class ReplyController implements ReplyClient {

    @Autowired
    ReplyService replyService;

    @Override
    public void publish(@Valid @RequestBody ReplyPublishDto publishDto){
        String peopleId = UserContext.getUser().getId();
        replyService.publish(peopleId,publishDto);
    }

    @Override
    public PageResult<ReplySimpleDto> list(@RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                           @RequestParam(required = false,name = "size",defaultValue = "10") Integer size,
                                           @RequestParam(name = "replyType" ) ReplyType replyType,
                                           @RequestParam(name = "resourceId" ) String resourceId
                                              ) {
        return replyService.list(page, size, replyType,resourceId);
    }

    @Override
    public Integer countByResourceIdAndCommentsType(@RequestParam(name = "resourceId" ) String resourceId,@RequestParam(name = "replyType" )  ReplyType replyType) {
        return replyService.countByResourceIdAndCommentsType(resourceId,replyType);
    }
}
