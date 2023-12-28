package com.biwhci.vistaback.poll.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PollOptionCreateDto {
    @NotBlank
    private String label;

    @JsonCreator
    public PollOptionCreateDto(@JsonProperty("label") String label) {
        this.label = label;
    }
}
