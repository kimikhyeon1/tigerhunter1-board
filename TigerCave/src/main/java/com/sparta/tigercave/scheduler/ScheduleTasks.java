package com.sparta.tigercave.scheduler;

import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.repository.CommentLikeRepository;
import com.sparta.tigercave.repository.CommentRepository;
import com.sparta.tigercave.service.CommentLikeService;
import com.sparta.tigercave.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleTasks {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    @Scheduled(cron = "0 0 5 * * ?")
    public void commentLikeCntSync() {

        //모든 코멘트 리스트 가져오기
        List<Comment> commentList = commentRepository.findAll();

        //코멘트가 있을 경우에만 실행
        commentList.stream().parallel().forEach(comment -> {
            Long commentId = comment.getId();
            Long cntLikeCnt = commentLikeRepository.countById(commentId);

            commentService.updateLikeByScheduler(commentId, cntLikeCnt);
        });

    }

}
