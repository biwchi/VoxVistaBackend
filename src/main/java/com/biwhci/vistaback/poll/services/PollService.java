package com.biwhci.vistaback.poll.services;

import com.biwhci.vistaback.exceptions.UnauthorizedException;
import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.mappers.PollMapper;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.poll.repositries.PollRepository;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class PollService {
  private final PollRepository pollRepository;
  private final PollMapper pollMapper;
  private final AppUserService appUserService;

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

  public List<PollDto> findAllPollsByUser() {
    Optional<AppUser> user = appUserService.getCurrentUser();

    if (user.isEmpty()) {
      throw new UnauthorizedException();
    }

    return pollRepository.findByCreatedByUserId(user.get().getUserId())
        .stream()
        .map(pollMapper::toDto)
        .toList();
  }

  public void createPoll(PollCreateDto pollCreateDto) {
    pollRepository.save(pollMapper.create(pollCreateDto));
  }

  public boolean isUserVoted(Poll poll) {
    AtomicBoolean isVoted = new AtomicBoolean(false);
    Optional<AppUser> user = appUserService.getCurrentUser();

    if (user.isEmpty()) {
      return isVoted.get();
    }

    List<PollOption> pollOptions = poll.getOptions();

    pollOptions.forEach(option -> {
      if (isVoted.get()) {
        return;
      }

      isVoted.set(option.getVoters().stream().anyMatch(voter -> voter.getUserId().equals(user.get().getUserId())));
    });

    return isVoted.get();
  }
}
