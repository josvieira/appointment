package com.example.appointment.challenge.services;

import com.example.appointment.challenge.dtos.AppointmentDto;
import com.example.appointment.challenge.enums.StatusAppointment;
import com.example.appointment.challenge.models.Appointment;
import com.example.appointment.challenge.repositories.AppointmentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${uri.system.legacy}")
    private String uri;


    public Appointment saveAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDto, appointment);
        try {
            String returnLegacy = WebClient.create().get()
                    .uri(uri , appointmentDto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("Conection legacy for appointment={}", returnLegacy );
            appointment.setStatusAppointment(StatusAppointment.SENT);
        } catch (Exception e) {
            appointment.setStatusAppointment(StatusAppointment.ERROR); //melhoria criar uma fila de retry
        } finally {
            log.info("Appointment saved in BD={}", appointment);
          return repository.save(appointment);
        }

    }


    public void sendAppointmentToQueue(String queue, Object message){
        rabbitTemplate.convertAndSend(queue, message);
        log.info("Appointment sent to queue ms.appoint.save", message);
    }
}
