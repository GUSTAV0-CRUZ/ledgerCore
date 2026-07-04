package dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.maps;

import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.model.UserModel;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

public class UserMap {
  public static User mapToUser(UserModel userModel) {
    return User.restore(
        userModel.getId(),
        userModel.getName(),
        userModel.getDateOfBirth(),
        userModel.getEmail(),
        userModel.getCpf()
    );
  }

  public static UserModel mapToUserModel(User user) {
    return new UserModel(
        user.getId(),
        user.getName(),
        user.getDateOfBirth(),
        user.getEmail(),
        user.getCpf().getCpfInString()
    );
  }
}
