package com.biwhci.vistaback.poll.mappers;

import com.biwhci.vistaback.poll.dtos.PollCommentCreateDto;
import com.biwhci.vistaback.poll.dtos.PollCommentDto;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollComment;
import com.biwhci.vistaback.user.mappers.AppUserMapper;
import com.biwhci.vistaback.user.models.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PollCommentMapper {
  private final AppUserMapper appUserMapper;

  public PollCommentDto toDto(PollComment pollComment) {
    return new PollCommentDto(
        pollComment.getPollCommentId(),
        pollComment.getContent(),
        pollComment.getCreatedAt(),
        appUserMapper.toDto(pollComment.getCreatedBy())
    );
  }

  public PollComment create(PollCommentCreateDto pollCommentCreateDto, AppUser appUser, Poll poll) {
    return new PollComment(
        pollCommentCreateDto.getContent(),
        poll,
        appUser
    );
  }
}
