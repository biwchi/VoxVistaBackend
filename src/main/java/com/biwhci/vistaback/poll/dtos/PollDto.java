package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.user.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PollDto {
  @JsonProperty(index = 0)
  private Integer id;
  @JsonProperty(index = 1)
  private String title;
  @JsonProperty(index = 2)
  private String description;
  @JsonProperty(index = 3)
  private Boolean multiple;
  @JsonProperty(index = 4)
  private Boolean anonymous;
  @JsonProperty(index = 5)
  private Boolean voted;
  @JsonProperty(index = 6)
  private ZonedDateTime startDate;
  @JsonProperty(index = 7)
  private ZonedDateTime endDate;
  @JsonProperty(index = 8)
  private ZonedDateTime createdAt;
  @JsonProperty(index = 9)
  private List<PollOptionDto> options;

  public PollDto(Poll poll) {
    this.id = poll.getPollId();
    this.title = poll.getTitle();
    this.description = poll.getDescription();
    this.multiple = poll.getMultiple();
    this.anonymous = poll.getAnonymous();
    this.startDate = poll.getStartDate();
    this.endDate = poll.getEndDate();
    this.createdAt = poll.getCreatedAt();
    this.options = poll.getOptions().stream().map(opt -> {
      PollOptionDto pollOption = new PollOptionDto();

      pollOption.setId(opt.getPollOptionId());
      pollOption.setLabel(opt.getLabel());
      pollOption.setVotes(opt.getVotes());

      return pollOption;
    }).toList();
  }
}