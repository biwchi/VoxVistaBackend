package com.biwhci.vistaback.poll.services;

import com.biwhci.vistaback.poll.dtos.PollCommentCreateDto;
import com.biwhci.vistaback.poll.dtos.PollCommentDto;
import com.biwhci.vistaback.poll.mappers.PollCommentMapper;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollComment;
import com.biwhci.vistaback.poll.repositries.PollCommentRepository;
import com.biwhci.vistaback.poll.repositries.PollRepository;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PollCommentService {
  private final PollCommentRepository pollCommentRepository;
  private final PollCommentMapper pollCommentMapper;
  private final PollRepository pollRepository;
  private final AppUserService appUserService;

  public List<PollCommentDto> getCommentsByPollId(Integer pollId) {
    return pollCommentRepository.findAllByPollPollId(pollId).stream().map(pollCommentMapper::toDto).toList();
  }

  public void createComment(PollCommentCreateDto pollCommentCreateDto) {
    AppUser appUser = appUserService.getCurrentUser().orElseThrow(() -> new RuntimeException("User not found"));
    Optional<Poll> poll = pollRepository.findById(pollCommentCreateDto.getPollId());

    if (poll.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll not found");
    }

    pollCommentRepository.save(pollCommentMapper.create(pollCommentCreateDto, appUser, poll.get()));
  }

  public void deleteComment(Integer id) {
    AppUser user = appUserService.getCurrentUser()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    PollComment pollComment = pollCommentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll comment not found"));


    if (!pollComment.getCreatedBy().getUserId().equals(user.getUserId())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to delete this comment");
    }

    pollCommentRepository.deleteById(id);
  }
}

