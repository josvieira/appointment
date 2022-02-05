package com.example.appointment.challenge.models;

import com.example.appointment.challenge.enums.StatusAppointment;
import lombok.Data;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_APPOINT")
public class Appointment {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String includedAt;

    private int employeeId;

    private int employerId;

    private StatusAppointment statusAppointment;

}
