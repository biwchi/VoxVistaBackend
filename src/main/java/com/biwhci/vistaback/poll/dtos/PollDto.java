package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PollDto {
  private Integer id;
  private String title;
  private String description;
  private Boolean multiple;
  private Boolean anonymous;
  private Boolean voted;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private ZonedDateTime startDate;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private ZonedDateTime endDate;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private ZonedDateTime createdAt;
  private AppUserDto createdBy;
  private List<PollOptionDto> options;

  private Boolean ended = true;
  private Boolean started = true;

  public PollDto(Poll poll) {
    if (poll.getStartDate() != null && ZonedDateTime.now().isBefore(poll.getStartDate())) {
      this.started = false;
    }
    if (poll.getEndDate() != null && ZonedDateTime.now().isBefore(poll.getEndDate())) {
      this.ended = false;
    }

    this.id = poll.getPollId();
    this.title = poll.getTitle();
    this.description = poll.getDescription();
    this.multiple = poll.getMultiple();
    this.anonymous = poll.getAnonymous();
    this.startDate = poll.getStartDate();
    this.endDate = poll.getEndDate();
    this.createdAt = poll.getCreatedAt();
    this.createdBy = new AppUserDto(poll.getCreatedBy());
    this.options = poll.getOptions().stream().map(opt -> {
      PollOptionDto pollOption = new PollOptionDto();

      pollOption.setId(opt.getPollOptionId());
      pollOption.setLabel(opt.getLabel());
      pollOption.setVotes(opt.getVotes());

      return pollOption;
    }).toList();
  }
}