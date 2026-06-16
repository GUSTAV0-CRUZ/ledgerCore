package dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.models;


import dev.cruzs.gustavo.service_bancary.domain.enums.AccountStatusEnum;
import dev.cruzs.gustavo.service_bancary.domain.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_account")
public class AccountModel implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private UUID id;

  @Column(nullable = false, name = "user_id")
  private UUID userId;

  @Column(nullable = false, name = "Bank_code")
  public Integer BANK_CODE;

  @Column(nullable = false, name = "Institution")
  public String INSTITUTION;

  @Column(nullable = false)
  private Integer agency;

  @Column(nullable = false)
  private String number;

  @Column(nullable = false)
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "type_account")
  private AccountTypeEnum typeAccount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "status_account")
  private AccountStatusEnum status;
}
