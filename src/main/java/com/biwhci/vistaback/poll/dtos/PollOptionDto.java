package com.biwhci.vistaback.poll.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollOptionDto {
  private Integer id;
  private String label;
  private Integer votes;
}
