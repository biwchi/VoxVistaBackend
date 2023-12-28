package com.biwhci.vistaback.poll.dtos;

import com.biwhci.vistaback.poll.models.PollOption;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PollCreateDto {
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;

    private final boolean isMultiple;
    private final boolean isAnonymous;

    @Future
    private final Date startDate;
    @Future
    private final Date endDate;

    @NotEmpty
    @Size(min = 2, max = 10)
    private final List<PollOptionCreateDto> options;

    public PollCreateDto(String title,
                         String description,
                         List<PollOption> options,
                         boolean isMultiple,
                         boolean isAnonymous,
                         Date startDate,
                         Date endDate) {
        this.title = title;
        this.description = description;
        this.options = options.stream().map(opt -> new PollOptionCreateDto(opt.getLabel())).toList();
        this.isMultiple = isMultiple;
        this.isAnonymous = isAnonymous;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
