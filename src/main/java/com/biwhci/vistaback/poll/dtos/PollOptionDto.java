package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.user.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollOptionDto {
  private Integer id;
  private String label;
  private Integer votes;
}
