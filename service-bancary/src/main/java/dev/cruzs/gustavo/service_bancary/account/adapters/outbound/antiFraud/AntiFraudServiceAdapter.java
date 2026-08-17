package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.antiFraud;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AntiFraudService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.EvaluateCommand;
import dev.cruzs.gustavo.service_bancary.history.adapters.inbound.gRPC.generated.FindAllByAccountIdAndYearMonthRequest;
import dev.cruzs.gustavo.service_bancary.history.adapters.inbound.gRPC.generated.ListHistoriesResponse;
import dev.cruzs.gustavo.service_bancary.history.adapters.inbound.gRPC.generated.HistoryServiceGrpc.HistoryServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class AntiFraudServiceAdapter implements AntiFraudService {
  private final static BigDecimal MOVEMENT_LIMIT = BigDecimal.valueOf(5000.00);

  @GrpcClient("history-service")
  private HistoryServiceBlockingStub historyServiceBlockingStub;

  @Override
  public void evaluate(EvaluateCommand evaluateCommand) {
    if (evaluateCommand.amount().compareTo(MOVEMENT_LIMIT) > 0)
      throw new IllegalArgumentException("Movement limit exceeded, limit: " + MOVEMENT_LIMIT);

    Instant nowInstant = Instant.now();
    YearMonth yearMonth = YearMonth.from(nowInstant.atZone(ZoneId.of("UTC")));


    var findAllByAccountIdAndYearMonthRequest = this.createFindAllByAccountIdAndYearMonthRequest(
        evaluateCommand.accountId(),
        yearMonth
    );

    ListHistoriesResponse listHistoriesResponse =  historyServiceBlockingStub.findAllByAccountIdAndYearMonth(
        findAllByAccountIdAndYearMonthRequest
    );

    long movements = listHistoriesResponse.getHistoriesResponseList().stream()
        .map(historyResponse -> Instant.parse(historyResponse.getTransferDate()))
        .filter(transferDate -> Duration.between(transferDate, nowInstant).abs().toMinutes() < 1)
        .limit(2)
        .count();

    if (movements == 2)
      throw new IllegalArgumentException("Too many movement per minute");
  }

  private FindAllByAccountIdAndYearMonthRequest createFindAllByAccountIdAndYearMonthRequest (
      UUID accountId,
      YearMonth yearMonth
  ) {
    return FindAllByAccountIdAndYearMonthRequest.newBuilder()
        .setAccountId(accountId.toString())
        .setYearMonth(yearMonth.toString())
        .build();
  }
}
