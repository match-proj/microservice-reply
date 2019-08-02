package com.match.reply.context.domain.service.impl;

import com.match.common.PageResult;
import com.match.reply.client.bean.ReplyPublishDto;
import com.match.reply.client.bean.ReplySimpleDto;
import com.match.reply.client.bean.ReplyType;
import com.match.reply.context.domain.entity.Reply;
import com.match.reply.context.domain.repostory.ReplyRepository;
import com.match.reply.context.domain.service.ReplyService;
import com.match.user.client.UserClient;
import com.match.user.client.bean.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhangchao
 * @Date 2019/5/28 10:04
 * @Version v1.0
 */
@Service
public class ReplyServiceImpl implements ReplyService {


    @Autowired
    ReplyRepository commentsRepository;

    @Autowired(required = false)
    UserClient userClient;


    @Override
    public void publish(String peopleId, ReplyPublishDto publishDto) {
        Reply comments = new Reply();
        comments.setCreateTime(new Date());
        comments.setResourceId(publishDto.getResourceId());
        comments.setLevel(publishDto.getCommentsId() == null ? 1 : 2);
        comments.setCommentsId(publishDto.getCommentsId());
        comments.setText(publishDto.getText());
        comments.setReplyType(publishDto.getReplyType());
        comments.setPeopleId(peopleId);

        Optional<UserInfoDTO> optionalPeople = Optional.ofNullable(userClient.getUser(peopleId));

        if (optionalPeople.isPresent()) {
            UserInfoDTO userInfoDTO = optionalPeople.get();
            comments.setNickName(userInfoDTO.getNickName());
            comments.setEncodedPrincipal(userInfoDTO.getEncodedPrincipal());

        }

        commentsRepository.save(comments);
    }


    @Override
    public PageResult<ReplySimpleDto> list(Integer page, Integer size, ReplyType replyType, String resourceId) {
        PageRequest of = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("createTime")));
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> pr = new ArrayList<>();
                pr.add(cb.equal(root.get("replyType").as(ReplyType.class), replyType));
                pr.add(cb.equal(root.get("resourceId").as(String.class), resourceId));

                return cb.and(pr.toArray(new Predicate[pr.size()]));
            }
        };
        Page<Reply> all = commentsRepository.findAll(spec, of);
        long totalElements = all.getTotalElements();
        List<Reply> content = all.getContent();

        List<ReplySimpleDto> collect = content.stream().map(item -> {
            ReplySimpleDto commentsSimpleDto = new ReplySimpleDto();
            commentsSimpleDto.setId(item.getId());
            commentsSimpleDto.setResourceId(item.getResourceId());
            commentsSimpleDto.setCreateTime(item.getCreateTime());
            commentsSimpleDto.setText(item.getText());
            commentsSimpleDto.setLevel(item.getLevel());
            commentsSimpleDto.setNickName(item.getNickName());
            commentsSimpleDto.setEncodedPrincipal(item.getEncodedPrincipal());

            if (item.getLevel() == 2 && item.getCommentsId() != null) {
                Optional<Reply> replyComments = commentsRepository.findById(item.getCommentsId());
                if (replyComments.isPresent()) {
                    commentsSimpleDto.setReplyNickName(replyComments.get().getNickName());
                }
            }

            return commentsSimpleDto;
        }).filter(item -> item != null).collect(Collectors.toList());


        return new PageResult<ReplySimpleDto>(totalElements, collect);
    }

    @Override
    public Integer countByResourceIdAndCommentsType(String resourceId, ReplyType replyType) {
        return commentsRepository.countByResourceIdAndReplyType(resourceId,replyType);
    }
}
