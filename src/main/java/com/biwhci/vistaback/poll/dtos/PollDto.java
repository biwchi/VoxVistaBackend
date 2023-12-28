package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.poll.models.PollOption;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PollDto {
  private final Integer id;
  private final String title;
  private final String description;
  private final boolean isMultiple;
  private final boolean isAnonymous;
  private final Date startDate;
  private final Date endDate;
  private final List<PollOptionDto> options;

  public PollDto(Integer id,
                 String title,
                 String description,
                 List<PollOption> options,
                 boolean isMultiple,
                 boolean isAnonymous, Date startDate, Date endDate) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.isMultiple = isMultiple;
    this.isAnonymous = isAnonymous;
    this.options = options.stream().map(opt -> new PollOptionDto(
        opt.getPollOptionId(),
        opt.getLabel(),
        opt.getVotes())).toList();
    this.startDate = startDate;
    this.endDate = endDate;
  }
}