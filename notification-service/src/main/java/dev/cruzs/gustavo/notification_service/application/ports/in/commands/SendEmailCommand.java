package dev.cruzs.gustavo.notification_service.application.ports.in.commands;

public record SendEmailCommand(String to, String subject, String text) {
}
