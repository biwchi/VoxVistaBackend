package com.biwhci.vistaback.poll.controllers;

import com.biwhci.vistaback.poll.dtos.PollOptionVoteDto;
import com.biwhci.vistaback.poll.services.PollOptionService;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/poll/option")
@RequiredArgsConstructor
public class PollOptionController {
  private final PollOptionService pollOptionService;


  @GetMapping("/{pollOptionId}/users")
  private final List<AppUserDto> getVoters(@PathVariable Integer pollOptionId) {
    return pollOptionService.getVoters(pollOptionId);
  }

  @PostMapping()
  public void voteForOption(@Valid @RequestBody PollOptionVoteDto pollOptionVoteDto, Principal principal) {
    pollOptionService.voteForOption(pollOptionVoteDto);
  }
}
