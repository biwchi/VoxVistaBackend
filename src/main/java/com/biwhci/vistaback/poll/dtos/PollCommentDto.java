package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class PollCommentDto {
  private Integer id;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private ZonedDateTime createdAt;

  private AppUserDto createdBy;
}
