package com.biwhci.vistaback.user.models;

import com.biwhci.vistaback.poll.models.PollOption;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {
  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Integer userId;

  private String nickname;
  private String email;
  private String password;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "poll_option_voter",
      joinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "poll_option_id", referencedColumnName = "poll_option_id")
      },
      uniqueConstraints = {
          @UniqueConstraint(columnNames = {"user_id", "poll_option_id"})
      }
  )
  @ToString.Exclude
  private List<PollOption> voted;

  public AppUser(String nickname, String email, String password) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
