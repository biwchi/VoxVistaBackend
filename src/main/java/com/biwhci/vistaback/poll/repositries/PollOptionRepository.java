package com.biwhci.vistaback.poll.repositries;

import com.biwhci.vistaback.poll.models.PollOption;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Integer> {
}
