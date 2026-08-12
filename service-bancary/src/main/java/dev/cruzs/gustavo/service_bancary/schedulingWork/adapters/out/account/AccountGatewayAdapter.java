package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.account;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.AccountGateway;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.commands.TransferMoneyGatewayCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class AccountGatewayAdapter implements AccountGateway{
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final StreamBridge streamBridge;

  public AccountGatewayAdapter(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  @Override
  public void transferMoney(TransferMoneyGatewayCommand transferMoneyGatewayCommand) {
    streamBridge.send("transferMoneyAccountProducer-out-0", transferMoneyGatewayCommand);

    logger.info(
        "Sender message to the producer: transferMoneyAccountProducer of user: ({})",
        transferMoneyGatewayCommand.senderUserId()
    );
  }
}
