package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands;

import java.time.Instant;
import java.util.UUID;

public record UpdateDateSchedulingTransferCommand(UUID schedulingTransferId, Instant newScheduledDate) {
}
