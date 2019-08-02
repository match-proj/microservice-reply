package com.match.reply.context.domain.repostory;

import com.match.reply.client.bean.ReplyType;
import com.match.reply.context.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by jagua on 2019/5/27.
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply,String>, JpaSpecificationExecutor {

    Integer countByResourceIdAndReplyType(String resourceId, ReplyType replyType);

}
