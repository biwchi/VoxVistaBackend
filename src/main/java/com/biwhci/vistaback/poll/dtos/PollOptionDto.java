package com.biwhci.vistaback.poll.dtos;

import lombok.Data;

@Data
public class PollOptionDto {
  private final Integer id;
  private final String label;
  private final Integer votes;

  public PollOptionDto(Integer id, String label, Integer votes) {
    this.id = id;
    this.label = label;
    this.votes = votes;
  }
}
