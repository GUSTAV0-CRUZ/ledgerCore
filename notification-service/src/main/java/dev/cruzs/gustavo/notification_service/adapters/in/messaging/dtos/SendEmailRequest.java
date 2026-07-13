package dev.cruzs.gustavo.notification_service.adapters.in.messaging.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendEmailRequest(
    @Email
    @NotBlank
    String to,

    @NotBlank
    String subject,

    @NotBlank
    String text
) {
}
