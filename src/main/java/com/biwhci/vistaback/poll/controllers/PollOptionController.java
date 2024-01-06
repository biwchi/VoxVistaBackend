package com.biwhci.vistaback.poll.controllers;

import com.biwhci.vistaback.poll.dtos.PollOptionVoteDto;
import com.biwhci.vistaback.poll.services.PollOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/poll/option")
@RequiredArgsConstructor
public class PollOptionController {
  private final PollOptionService pollOptionService;

  @PostMapping()
  public void voteForOption(@Valid @RequestBody PollOptionVoteDto pollOptionVoteDto, Principal principal) {
    pollOptionService.voteForOption(pollOptionVoteDto);
  }
}
