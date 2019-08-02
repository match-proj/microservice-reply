package com.match.reply.context.domain.entity;

import com.match.reply.client.bean.ReplyType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author zhangchao
 * @Date 2019/5/24 15:15
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class Reply {


    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private Date createTime;

    @Column
    private String resourceId;

    @Column
    private Integer level;//深度，最多2层

    @Column
    private String commentsId; //跟评ID

    @Column
    private String text;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ReplyType replyType;

    @Column
    private String peopleId;

    @Column
    private String nickName;

    @Column
    private String encodedPrincipal;




}
