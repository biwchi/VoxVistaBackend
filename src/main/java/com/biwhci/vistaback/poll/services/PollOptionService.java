package com.biwhci.vistaback.poll.services;

import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.poll.repositries.PollOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PollOptionService {
  private final PollOptionRepository pollOptionRepository;

  public void voteForOption(Integer id) {
    Optional<PollOption> pollOption = pollOptionRepository.findById(id);
    Poll poll = pollOption.get().getPoll();

    if (pollOption.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Such poll option does not exist");
    }

    pollOption.get().setVotes(pollOption.get().getVotes() + 1);
    pollOptionRepository.save(pollOption.get());
  }
}
