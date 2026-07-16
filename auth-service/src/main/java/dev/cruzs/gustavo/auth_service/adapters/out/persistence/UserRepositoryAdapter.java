package dev.cruzs.gustavo.auth_service.adapters.out.persistence;

import dev.cruzs.gustavo.auth_service.adapters.out.persistence.maps.UserMap;
import dev.cruzs.gustavo.auth_service.adapters.out.persistence.models.UserModel;
import dev.cruzs.gustavo.auth_service.adapters.out.persistence.repository.UserJpaRepository;
import dev.cruzs.gustavo.auth_service.application.ports.out.UserRepository;
import dev.cruzs.gustavo.auth_service.domain.User;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryAdapter implements UserRepository {
  private final UserJpaRepository userJpaRepository;

  public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public User save(User user) {
    UserModel userModel = UserMap.toUserModel(user);
    UserModel userModelSave = this.userJpaRepository.save(userModel);

    return UserMap.toUser(userModelSave);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return this.userJpaRepository.findById(id).map(UserMap::toUser);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return this.userJpaRepository.findByEmail(email).map(UserMap::toUser);
  }
}
