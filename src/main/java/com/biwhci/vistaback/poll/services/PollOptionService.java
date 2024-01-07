package com.biwhci.vistaback.poll.services;

import com.biwhci.vistaback.poll.dtos.PollOptionVoteDto;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.poll.repositries.PollOptionRepository;
import com.biwhci.vistaback.poll.repositries.PollRepository;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PollOptionService {
  private final PollOptionRepository pollOptionRepository;
  private final PollRepository pollRepository;
  private final AppUserService appUserService;

  public void voteForOption(PollOptionVoteDto pollOptionVoteDto) {
    AppUserDto principal = (AppUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<AppUser> user = appUserService.findByEmail(principal.getEmail());

    Poll poll = pollRepository.findById(pollOptionVoteDto.getPollId()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, pollOptionVoteDto.getPollId() + "Such poll does not exist")
    );

    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
    }

    if (!poll.getMultiple() && pollOptionVoteDto.getOptionsIds().size() > 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only vote for one option in multiple choice poll");
    }

    if (poll.getStartDate() != null && poll.getStartDate().isAfter(ZonedDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll is not started yet");
    } else if (poll.getEndDate() != null && poll.getEndDate().isBefore(ZonedDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll is ended");
    }

    List<PollOption> pollOptions = pollOptionVoteDto.getOptionsIds()
        .stream()
        .map(id -> {
          Optional<PollOption> pollOption = pollOptionRepository.findById(id);

          if (pollOption.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + " Such poll option does not exist");
          }

          return pollOption.get();
        }).toList();

    pollOptions.forEach(pollOption -> {
      if (pollOption.getVoters().contains(user.get())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have already voted for this option");
      }

      pollOption.getVoters().add(user.get());
      pollOptionRepository.save(pollOption);
    });
  }
}
