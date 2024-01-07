package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.user.dtos.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class PollCommentDto {
  private Integer id;
  private String content;
  private ZonedDateTime createdAt;
  private AppUserDto createdBy;
}
