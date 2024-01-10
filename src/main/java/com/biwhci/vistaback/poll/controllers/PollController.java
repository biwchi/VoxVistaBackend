package com.biwhci.vistaback.poll.controllers;

import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.services.PollService;
import jakarta.validation.Valid;
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

  @GetMapping("/{pollId}")
  public PollDto findPollById(@PathVariable Integer pollId) {
    return pollService.findPollById(pollId);
  }

  @GetMapping("/user")
  public List<PollDto> findAllPollsByUser() {
    return pollService.findAllPollsByUser();
  }

  @PostMapping
  public void createPoll(@Valid @RequestBody PollCreateDto pollCreateDto) {
    pollService.createPoll(pollCreateDto);
  }
}
