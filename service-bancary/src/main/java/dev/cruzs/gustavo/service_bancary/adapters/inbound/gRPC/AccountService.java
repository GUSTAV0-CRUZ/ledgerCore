package dev.cruzs.gustavo.service_bancary.adapters.inbound.gRPC;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.FindAccountByUserIdUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.FindAccountByUserIdCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import dev.cruzs.gustavo.service_bancary.adapters.inbound.gRPC.AccountServiceGrpc.AccountServiceImplBase;

import java.util.UUID;

@GrpcService
public class AccountService extends AccountServiceImplBase {
  private final FindAccountByUserIdUseCase findAccountByUserIdUseCase;

  public AccountService(FindAccountByUserIdUseCase findAccountByUserIdUseCase) {
    this.findAccountByUserIdUseCase = findAccountByUserIdUseCase;
  }

  @Override
  public void findAccountByUserId(FindAccountByUserIdRequest request, StreamObserver<AccountResponse> responseObserver) {
    UUID userId = UUID.fromString(request.getUserId());
    Account account = this.findAccountByUserIdUseCase.execute(new FindAccountByUserIdCommand(userId));

    AccountResponse accountResponse = AccountResponse.newBuilder()
        .setId(account.getId().toString())
        .setUserId(account.getUserId().toString())
        .setAgency(account.getAgency())
        .setNumber(account.getNumber())
        .setBalance(account.getBalance().toString())
        .setTypeAccount(AccountTypeEnum.valueOf(account.getTypeAccount().name()))
        .setStatus(AccountStatusEnum.valueOf(account.getStatus().name()))
        .build();

    responseObserver.onNext(accountResponse);
    responseObserver.onCompleted();
  }
}
