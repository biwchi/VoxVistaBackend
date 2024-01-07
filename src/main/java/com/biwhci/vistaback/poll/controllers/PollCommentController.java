package com.biwhci.vistaback.poll.controllers;

import com.biwhci.vistaback.poll.dtos.PollCommentCreateDto;
import com.biwhci.vistaback.poll.dtos.PollCommentDto;
import com.biwhci.vistaback.poll.services.PollCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poll/comment")
public class PollCommentController {
  private final PollCommentService pollCommentService;

  @GetMapping("/{pollId}")
  public List<PollCommentDto> getCommentsByPollId(@PathVariable Integer pollId) {
    return pollCommentService.getCommentsByPollId(pollId);
  }

  @PostMapping
  public void createComment(@Valid @RequestBody PollCommentCreateDto pollCommentCreateDto) {
    pollCommentService.createComment(pollCommentCreateDto);
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable Integer id) {
    pollCommentService.deleteComment(id);
  }
}
