package dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserModel {
  @Id
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(nullable = false, unique = true)
  private String email;
  
  @Column(nullable = false)
  private String password;
}
