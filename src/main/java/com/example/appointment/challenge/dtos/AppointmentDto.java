package com.example.appointment.challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AppointmentDto {

    @Schema(example = "2021-03-15 15:10:00")
    @NotBlank(message = "includedAt required")
    private String includedAt;

    @Schema(example = "123")
    private int employeeId;

    @Schema(example = "999")
    private int employerId;
}
