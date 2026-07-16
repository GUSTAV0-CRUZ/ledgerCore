package dev.cruzs.gustavo.auth_service.adapters.out.persistence.maps;

import dev.cruzs.gustavo.auth_service.adapters.out.persistence.models.UserModel;
import dev.cruzs.gustavo.auth_service.domain.User;
import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;

public class UserMap {
  public static User toUser(UserModel userModel) {
    return User.restore(
      userModel.getId(),
      userModel.getUsername(),
      new Email(userModel.getEmail()),
      userModel.getPassword()
    );
  }

  public static UserModel toUserModel(User user) {
    return new UserModel(
      user.getId(),
      user.getUsername(),
      user.getEmail().getValue(),
      user.getPassword()
    );
  }
}
