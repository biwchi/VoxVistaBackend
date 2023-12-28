package com.biwhci.vistaback.poll.repositries;

import com.biwhci.vistaback.poll.models.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Integer> {
}
