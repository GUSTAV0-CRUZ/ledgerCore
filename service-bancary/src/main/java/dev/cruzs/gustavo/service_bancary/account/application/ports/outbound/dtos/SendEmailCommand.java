package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos;

public record SendEmailCommand(String to, String subject, String text) {
}
