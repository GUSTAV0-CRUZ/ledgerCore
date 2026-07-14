package dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC;

import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC.generated.AccountResponse;
import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC.generated.AccountStatusEnum;
import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC.generated.AccountTypeEnum;
import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC.generated.FindAccountByUserIdRequest;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.FindAccountByUserIdUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.FindAccountByUserIdCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC.generated.AccountServiceGrpc.AccountServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GrpcService
public class AccountService extends AccountServiceImplBase {
  private final Logger logger = LoggerFactory.getLogger(AccountService.class);
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
        .setAgency(Account.AGENCY)
        .setNumber(account.getNumber().getNumber())
        .setBalance(account.getBalance().toString())
        .setTypeAccount(AccountTypeEnum.valueOf(account.getTypeAccount().name()))
        .setStatus(AccountStatusEnum.valueOf(account.getStatus().name()))
        .build();

    responseObserver.onNext(accountResponse);
    logger.info("find account with id: {}", account.getId());
    responseObserver.onCompleted();
  }
}
