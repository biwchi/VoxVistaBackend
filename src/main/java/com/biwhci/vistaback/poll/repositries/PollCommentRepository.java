package com.biwhci.vistaback.poll.repositries;

import com.biwhci.vistaback.poll.models.PollComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollCommentRepository extends JpaRepository<PollComment, Integer> {
  List<PollComment> findAllByPollPollId(Integer pollId);
}
