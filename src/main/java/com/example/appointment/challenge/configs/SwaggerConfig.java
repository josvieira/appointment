package com.example.appointment.challenge.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Appointment Challenge",
                description = "Api para marcação de pontos, capaz de servir a diversos clients",
                contact = @Contact(name = "Josiene", url = "") ) )
public class SwaggerConfig {
}
