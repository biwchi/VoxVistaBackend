package com.biwhci.vistaback.poll.models;

import com.biwhci.vistaback.user.models.AppUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Poll {
  @Id
  @GeneratedValue
  @Column(name = "poll_id")
  private Integer pollId;

  @Column(nullable = false)
  private String title;
  @Column(columnDefinition = "text", nullable = false)
  private String description;

  @Column(nullable = false)
  private Boolean multiple;
  @Column(nullable = false)
  private Boolean anonymous;

  private ZonedDateTime startDate;
  private ZonedDateTime endDate;

  @CreationTimestamp
  private ZonedDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser createdBy;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "poll_id")
  private List<PollOption> options = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "poll_id")
  private List<PollComment> comments = new ArrayList<>();

  public Poll(String title,
              String description,
              boolean isMultiple,
              boolean isAnonymous,
              ZonedDateTime startDate,
              ZonedDateTime endDate,
              AppUser createdBy,
              List<PollOption> options
  ) {
    this.createdBy = createdBy;
    this.title = title;
    this.description = description;
    this.multiple = isMultiple;
    this.anonymous = isAnonymous;
    this.startDate = startDate;
    this.endDate = endDate;
    this.options = options;
  }
}
