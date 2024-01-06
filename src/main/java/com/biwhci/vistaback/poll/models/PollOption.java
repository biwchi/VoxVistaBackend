package com.biwhci.vistaback.poll.models;

import com.biwhci.vistaback.user.models.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.List;
import java.util.Set;

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

  @ManyToMany(cascade = CascadeType.MERGE)
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
  private List<User> voters;

  public PollOption(String label) {
    this.label = label;
  }
}
