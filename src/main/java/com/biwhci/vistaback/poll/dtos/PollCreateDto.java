package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.utils.DatesUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PollCreateDto {
  @NotBlank
  private String title;
  @NotBlank
  private  String description;

  private Boolean multiple;
  private Boolean anonymous;

  @Future
  private ZonedDateTime startDate = null;
  @Future
  private ZonedDateTime endDate = null;

  @NotEmpty
  @Size(min = 2, max = 10)
  private List<PollOptionCreateDto> options;
}