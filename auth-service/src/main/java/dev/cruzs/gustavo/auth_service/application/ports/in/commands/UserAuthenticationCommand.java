package dev.cruzs.gustavo.auth_service.application.ports.in.commands;

public record UserAuthenticationCommand(String email, String password) {
}
