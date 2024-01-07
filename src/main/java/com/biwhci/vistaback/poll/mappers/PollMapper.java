package com.biwhci.vistaback.poll.mappers;

import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class PollMapper {
  private final AppUserService appUserService;

  public PollDto toDto(Poll poll) {
    Optional<AppUser> user = appUserService.getCurrentUser();
    AtomicBoolean isVoted = new AtomicBoolean(false);
    List<PollOption> options = poll.getOptions();

    options.forEach(option -> {
      if (isVoted.get() || user.isEmpty()) {
        return;
      }

      if (appUserService.findUserByUserIdAndVotedPollOptionId(user.get().getUserId(), option.getPollOptionId()).isPresent()) {
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

