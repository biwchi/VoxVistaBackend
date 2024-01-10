package com.biwhci.vistaback.user.repositories;

import com.biwhci.vistaback.user.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
  Optional<AppUser> findByEmail(String email);

  List<AppUser> findByVotedPollOptionId(Integer pollOptionId);
}
