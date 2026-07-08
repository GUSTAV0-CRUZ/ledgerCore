package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.gRPC;

import dev.cruzs.gustavo.service_bancary.history.adapters.inbound.gRPC.generated.*;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindAllHistoryByAccountIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindHistoryByIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindAllHistoryByAccountIdCommand;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindHistoryByIdCommand;
import dev.cruzs.gustavo.service_bancary.history.domain.History;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.UUID;

@GrpcService
public class HistoryService extends HistoryServiceGrpc.HistoryServiceImplBase {
  private final FindAllHistoryByAccountIdUseCase findAllHistoryByAccountIdUseCase;
  private  final FindHistoryByIdUseCase findHistoryByIdUseCase;

  public HistoryService(
      FindAllHistoryByAccountIdUseCase findAllHistoryByAccountIdUseCase,
      FindHistoryByIdUseCase findHistoryByIdUseCase
  ) {
    this.findAllHistoryByAccountIdUseCase = findAllHistoryByAccountIdUseCase;
    this.findHistoryByIdUseCase = findHistoryByIdUseCase;
  }

  @Override
  public void findAllHistoryByAccountId(
      FindAllHistoryByAccountIdRequest request,
      StreamObserver<ListHistoriesResponse> responseObserver
  ) {
    UUID accountId = UUID.fromString(request.getAccountId());
    List<History> histories = this.findAllHistoryByAccountIdUseCase.execute(
        new FindAllHistoryByAccountIdCommand(accountId)
    );

    ListHistoriesResponse.Builder listHistoriesResponse = ListHistoriesResponse.newBuilder();
    histories.forEach(
    history ->
      listHistoriesResponse.addHistoriesResponse(
        HistoryResponse.newBuilder()
          .setId(history.getId().toString())
          .setAccountId(history.getAccountId().toString())
          .setAmount(history.getAmount().toString())
          .setInstitutionName(history.getInstitutionName())
          .setDestinataryName(history.getDestinataryName())
          .setTransferDate(history.getTransferDate().toString())
        .build()
      )
    );

    responseObserver.onNext(listHistoriesResponse.build());
    responseObserver.onCompleted();
  }

  @Override
  public void findHistoryById(FindHistoryByIdRequest request, StreamObserver<HistoryResponse> responseObserver) {
    UUID id = UUID.fromString(request.getId());
    History history = this.findHistoryByIdUseCase.execute(
        new FindHistoryByIdCommand(id)
    );

    HistoryResponse historyResponse = HistoryResponse.newBuilder()
        .setId(history.getId().toString())
        .setAccountId(history.getAccountId().toString())
        .setAmount(history.getAmount().toString())
        .setInstitutionName(history.getInstitutionName())
        .setDestinataryName(history.getDestinataryName())
        .setTransferDate(history.getTransferDate().toString())
      .build();

    responseObserver.onNext(historyResponse);
    responseObserver.onCompleted();
  }
}
