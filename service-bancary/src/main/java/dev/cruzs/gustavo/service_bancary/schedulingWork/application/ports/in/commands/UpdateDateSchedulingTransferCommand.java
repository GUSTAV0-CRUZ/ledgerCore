package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateDateSchedulingTransferCommand(UUID schedulingTransferId, LocalDateTime newScheduledDate) {
}
