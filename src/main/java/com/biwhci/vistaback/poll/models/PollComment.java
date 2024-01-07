package com.biwhci.vistaback.poll.models;

import com.biwhci.vistaback.user.models.AppUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class PollComment {
  @Id
  @GeneratedValue
  @Column(name = "poll_comment_id")
  private Integer pollCommentId;

  @Column(columnDefinition = "text", nullable = false)
  private String content;

  @CreationTimestamp
  private ZonedDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "poll_id")
  private Poll poll;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser createdBy;

  public PollComment(String content, Poll poll, AppUser createdBy) {
    this.content = content;
    this.poll = poll;
    this.createdBy = createdBy;
  }
}
