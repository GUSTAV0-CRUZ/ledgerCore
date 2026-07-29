package dev.cruzs.gustavo.gateway_service.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

import java.nio.charset.StandardCharsets;

public class ConversionsOfTypes {
  public static byte[] protobufToJson(MessageOrBuilder messageOrBuilder) {
    try {
      return JsonFormat.printer()
          .print(messageOrBuilder)
          .getBytes(StandardCharsets.UTF_8);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
  }
}
