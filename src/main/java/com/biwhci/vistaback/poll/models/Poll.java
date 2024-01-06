package com.biwhci.vistaback.poll.models;

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

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "poll_id")
  private List<PollOption> options = new ArrayList<>();

  public Poll(String title,
              String description,
              boolean isMultiple,
              boolean isAnonymous,
              ZonedDateTime startDate,
              ZonedDateTime endDate,
              List<PollOption> options
  ) {
    this.title = title;
    this.description = description;
    this.multiple = isMultiple;
    this.anonymous = isAnonymous;
    this.startDate = startDate;
    this.endDate = endDate;
    this.options = options;
  }
}
