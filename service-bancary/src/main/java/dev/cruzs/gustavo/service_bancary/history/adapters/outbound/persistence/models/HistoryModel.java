package dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_history")
@Entity
public class HistoryModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private UUID id;

  @Column(nullable = false, name = "account_id")
  private UUID accountId;

  @Column(nullable = false, name = "destinatary_name")
  private String destinataryName;

  @Column(nullable = false, name = "institution_name")
  private String institutionName;

  @Column(nullable = false, name = "transfer_date")
  private LocalDateTime transferDate;
}
