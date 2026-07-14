package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.maps;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.models.AccountModel;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.valueObjects.NumberAccount;

public class AccountMap {
  public static Account mapToAccount(AccountModel accountModel) {
    return Account.restore(
        accountModel.getId(),
        accountModel.getUserId(),
        accountModel.getAgency(),
        NumberAccount.restore(accountModel.getNumber()),
        accountModel.getBalance(),
        accountModel.getTypeAccount(),
        accountModel.getStatus()
    );
  }

  public static AccountModel mapToAccountModel(Account account) {
    return new AccountModel(
        account.getId(),
        account.getUserId(),
        Account.BANK_CODE,
        Account.INSTITUTION,
        account.getAgency(),
        account.getNumber().getNumber(),
        account.getBalance(),
        account.getTypeAccount(),
        account.getStatus()
    );
  }
}
