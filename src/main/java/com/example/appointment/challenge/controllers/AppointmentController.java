package com.example.appointment.challenge.controllers;

import com.example.appointment.challenge.dtos.AppointmentDto;
import com.example.appointment.challenge.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/appointments")
@Log4j2
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @PostMapping("/")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create appointment")
    @ApiResponses(value = {
            @ApiResponse(description = "Appointment enviado para fila e para o legacy", responseCode = "201",
                        content = @Content(schema = @Schema(implementation = AppointmentDto.class)) )})
    public ResponseEntity createAppointment(@Valid @RequestBody AppointmentDto appointmentDto){//preciso validar melhor esses dados de entrada para evitar xss
        System.out.println(queue);
        service.sendAppointmentToQueue(queue, appointmentDto);
        log.info("Appointment received in api={}", appointmentDto);
        return new ResponseEntity(CREATED);
    }
}
