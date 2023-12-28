package com.biwhci.vistaback.poll.controllers;

import com.biwhci.vistaback.poll.services.PollOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poll/option")
@RequiredArgsConstructor
public class PollOptionController {
  private final PollOptionService pollOptionService;

  @PostMapping("/{id}")
  public void voteForOption(@PathVariable Integer id) {
    pollOptionService.voteForOption(id);
  }
}
