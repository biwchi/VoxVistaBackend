package com.biwhci.vistaback.poll.repositries;

import com.biwhci.vistaback.poll.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
  List<Poll> findByCreatedByUserId(Integer userId);
}
