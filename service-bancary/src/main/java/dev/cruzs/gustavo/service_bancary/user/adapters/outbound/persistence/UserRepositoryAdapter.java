package dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence;

import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.maps.UserMap;
import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.model.UserModel;
import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.repository.UserJpaRepository;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.UserRepository;
import dev.cruzs.gustavo.service_bancary.user.domain.User;
import dev.cruzs.gustavo.service_bancary.user.domain.exceptions.DuplicateAttributeUserException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryAdapter implements UserRepository {
  private final UserJpaRepository userJpaRepository;

  public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public User save(User user) {
    UserModel userModel = UserMap.mapToUserModel(user);

    try {
      userJpaRepository.save(userModel);

      return UserMap.mapToUser(userModel);
    } catch (DataIntegrityViolationException error) {
      throw new DuplicateAttributeUserException("Duplicate attribute in user", error);
    }
  }

  @Override
  public Optional<User> findById(UUID id) {
    UserModel userModel = userJpaRepository.findById(id).orElse(null);

    if (userModel == null) return Optional.empty();
    
    return Optional.of(UserMap.mapToUser(userModel));
  }
}
