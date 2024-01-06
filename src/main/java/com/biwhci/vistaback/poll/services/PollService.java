package com.biwhci.vistaback.poll.services;

import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.mappers.PollMapper;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.poll.repositries.PollOptionRepository;
import com.biwhci.vistaback.poll.repositries.PollRepository;
import com.biwhci.vistaback.user.dtos.UserDto;
import com.biwhci.vistaback.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class PollService {
  private final PollRepository pollRepository;
  private final PollMapper pollMapper;

  public List<PollDto> findAllPolls() {
    return pollRepository.findAll()
        .stream()
        .map(pollMapper::toDto)
        .toList();
  }

  public PollDto findPollById(Integer pollId) {
    Optional<Poll> poll = pollRepository.findById(pollId);

    if (poll.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Such poll does not exist");
    }

    return pollMapper.toDto(poll.get());
  }

  public void createPoll(PollCreateDto pollCreateDto) {
    pollRepository.save(pollMapper.create(pollCreateDto));
  }
}
