package com.biwhci.vistaback.poll.controllers;

import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.services.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poll")
@RequiredArgsConstructor
public class PollController {
  private final PollService pollService;

  @GetMapping
  public List<PollDto> findAllPolls() {
    return pollService.findAllPolls();
  }

  @PostMapping
  public void createPoll(@RequestBody PollCreateDto pollCreateDto) {
    pollService.createPoll(pollCreateDto);
  }
}
