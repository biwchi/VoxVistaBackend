package com.biwhci.vistaback.poll.mappers;

import com.biwhci.vistaback.exceptions.UnauthorizedException;
import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.poll.services.PollService;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.services.AppUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PollMapper {
  private final PollService pollService;
  private final AppUserService appUserService;

  public PollMapper(@Lazy PollService pollService, AppUserService appUserService) {
    this.pollService = pollService;
    this.appUserService = appUserService;
  }

  public PollDto toDto(Poll poll) {
    boolean isVoted = pollService.isUserVoted(poll);

    PollDto pollDto = new PollDto(poll);
    pollDto.setVoted(isVoted);

    return pollDto;
  }

  public Poll create(PollCreateDto pollCreateDto) {
    Optional<AppUser> user = appUserService.getCurrentUser();
    List<PollOption> options = pollCreateDto.getOptions().stream().map(opt -> new PollOption(opt.getLabel())).toList();

    if(user.isEmpty()) {
      throw new UnauthorizedException();
    }

    return new Poll(
        pollCreateDto.getTitle(),
        pollCreateDto.getDescription(),
        pollCreateDto.getMultiple(),
        pollCreateDto.getAnonymous(),
        pollCreateDto.getStartDate(),
        pollCreateDto.getEndDate(),
        user.get(),
        options
    );
  }
}

