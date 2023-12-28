package com.biwhci.vistaback.poll.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollOption {
  @Id
  @GeneratedValue
  @Column(name = "poll_option_id")
  private Integer pollOptionId;
  private String label;
  private Integer votes;

  @ManyToOne
  @JoinColumn(name = "poll_id")
  private Poll poll;

  public PollOption(String label) {
    this.label = label;
    this.votes = 0;
  }
}
