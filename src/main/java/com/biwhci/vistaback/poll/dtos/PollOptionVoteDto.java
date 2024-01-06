package com.biwhci.vistaback.poll.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PollOptionVoteDto {
  @NotNull
  @Min(1)
  private Integer pollId;

  @NotEmpty
  @Size(min = 1)
  private List<Integer> optionsIds;
}

