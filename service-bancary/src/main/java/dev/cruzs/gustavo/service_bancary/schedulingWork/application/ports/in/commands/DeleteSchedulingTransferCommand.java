package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands;

import java.util.UUID;

public record DeleteSchedulingTransferCommand(UUID schedulingTransferId) {
}
