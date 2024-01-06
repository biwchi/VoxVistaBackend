package com.biwhci.vistaback.poll.mappers;

import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.user.dtos.UserDto;
import com.biwhci.vistaback.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class PollMapper {
  private final UserService userService;

  public PollDto toDto(Poll poll) {
    Optional<UserDto> user = userService.getCurrentUser();
    AtomicBoolean isVoted = new AtomicBoolean(false);
    List<PollOption> options = poll.getOptions();

    options.forEach(option -> {
      if (isVoted.get() || user.isEmpty()) {
        return;
      }

      if (userService.findUserByUserIdAndVotedPollOptionId(user.get().getId(), option.getPollOptionId()).isPresent()) {
        isVoted.set(true);
      }
    });

    PollDto pollDto = new PollDto(poll);
    pollDto.setVoted(isVoted.get());

    return pollDto;
  }

  public Poll create(PollCreateDto pollCreateDto) {
    List<PollOption> options = pollCreateDto.getOptions().stream().map(opt -> new PollOption(opt.getLabel())).toList();

    return new Poll(
        pollCreateDto.getTitle(),
        pollCreateDto.getDescription(),
        pollCreateDto.getMultiple(),
        pollCreateDto.getAnonymous(),
        pollCreateDto.getStartDate(),
        pollCreateDto.getEndDate(),
        options
    );
  }
}

