package com.biwhci.vistaback.poll.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PollCommentCreateDto {
  @NotBlank
  @Size(max = 350)
  private String content;

  @Min(1)
  private Integer pollId;
}
