package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.models;

import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.enums.SchedulingEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "tb_scheduling")
public class SchedulingModel {
  @Id
  private UUID id;

  @Column(name = "sender_user_id", nullable = false)
  private UUID senderUserId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "recipient_number_account",  nullable = false)
  private String recipientNumberAccount;

  @Column(name = "scheduled_date",  nullable = false)
  private LocalDateTime scheduledDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SchedulingEnum status;
}
