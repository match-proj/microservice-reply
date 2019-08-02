package com.match.reply.client.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author zhangchao
 * @Date 2019/5/28 10:07
 * @Version v1.0
 */
@Getter
@Setter
public class ReplyPublishDto {

    @NotNull
    private String resourceId;

    private String commentsId;
    @NotNull
    private ReplyType replyType;
    @NotNull
    private String text;
}
