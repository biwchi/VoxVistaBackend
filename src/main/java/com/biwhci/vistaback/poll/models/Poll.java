package com.biwhci.vistaback.poll.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
  @Id
  @GeneratedValue
  @Column(name = "poll_id")
  private Integer id;

  @Column(nullable = false)
  private String title;
  @Column(columnDefinition = "text", nullable = false)
  private String description;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean isMultiple;
  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean isAnonymous;

  private Date startDate;
  private Date endDate;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "poll_id")
  private List<PollOption> options;
}
