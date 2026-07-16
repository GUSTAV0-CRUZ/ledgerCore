package dev.cruzs.gustavo.auth_service.application.ports.out;

import dev.cruzs.gustavo.auth_service.application.ports.out.commands.GenerateJwtCommand;

public interface Jwt {
   String generate(GenerateJwtCommand generateJwtCommand);
}
