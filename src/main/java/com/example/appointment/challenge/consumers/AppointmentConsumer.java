package com.example.appointment.challenge.consumers;

import com.example.appointment.challenge.dtos.AppointmentDto;
import com.example.appointment.challenge.models.Appointment;
import com.example.appointment.challenge.services.AppointmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AppointmentConsumer {

    @Autowired
    AppointmentService appointmentService;

    //usando o exchage default
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload AppointmentDto appointmentDto){
        Appointment appointment = appointmentService.saveAppointment(appointmentDto);
        log.info("Consumer listened appointment={}", appointmentDto);
        System.out.println("Appointment Save" + appointment.toString());
    }
}
