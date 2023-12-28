package com.biwhci.vistaback.poll.services;

import com.biwhci.vistaback.poll.dtos.PollCreateDto;
import com.biwhci.vistaback.poll.dtos.PollDto;
import com.biwhci.vistaback.poll.models.Poll;
import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.poll.repositries.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {
  private final PollRepository pollRepository;

  public List<PollDto> findAllPolls() {
    return pollRepository.findAll()
        .stream()
        .map(poll ->
            new PollDto(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getOptions(),
                poll.isMultiple(),
                poll.isAnonymous(),
                poll.getStartDate(),
                poll.getEndDate()
            ))
        .toList();
  }

  public void createPoll(PollCreateDto pollCreateDto) {
    System.out.println(pollCreateDto.toString());

    if (
        pollCreateDto.getStartDate() != null
            && pollCreateDto.getEndDate() != null
            && !pollCreateDto.getStartDate().before(pollCreateDto.getEndDate())
    ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be greater than end date");
    }

    Poll poll = new Poll();
    List<PollOption> options = pollCreateDto.getOptions().stream().map(opt -> new PollOption(opt.getLabel())).toList();

    poll.setTitle(pollCreateDto.getTitle());
    poll.setDescription(pollCreateDto.getDescription());
    poll.setOptions(options);

    pollRepository.save(poll);
  }
}
