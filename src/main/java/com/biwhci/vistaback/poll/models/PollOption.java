package com.biwhci.vistaback.poll.models;

import com.biwhci.vistaback.user.models.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PollOption {
  @Id
  @GeneratedValue
  @Column(name = "poll_option_id")
  private Integer pollOptionId;

  private String label;

  @Formula(value = "(SELECT COUNT(*) FROM poll_option_voter pv WHERE pv.poll_option_id = poll_option_id)")
  private Integer votes;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "poll_option_voter",
      joinColumns = {
          @JoinColumn(name = "poll_option_id", referencedColumnName = "poll_option_id")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id")
      },
      uniqueConstraints = {
          @UniqueConstraint(columnNames = {"poll_option_id", "user_id"})
      }
  )
  @JsonIgnoreProperties("voted")
  private List<AppUser> voters;

  public PollOption(String label) {
    this.label = label;
  }
}
