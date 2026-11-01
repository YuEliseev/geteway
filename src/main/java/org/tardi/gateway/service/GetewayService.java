package org.tardi.gateway.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.tardi.gateway.model.records.MessageDto;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class GetewayService {

    private static final Logger LOG = Logger.getLogger(String.valueOf(GetewayService.class));

//    private static final Logger LOG = Logger.getLogger(GatewayService.class);

    // Внедряем Emitter для канала gateway-out, который мы настроили
    @Inject
    @Channel("gateway-out")
    Emitter<MessageDto> emitter;

    public MessageDto send(MessageDto request) {
        LOG.log(Level.INFO,
             String.format("Отправка в RabbitMQ: value=%s, code=%s",
                     request.value(),
                     request.code()));

        // Отправляем сообщение в RabbitMQ
        emitter.send(request);

        // Возвращаем ответ клиенту (пока просто эхо, чтобы Postman видел результат)
        return new MessageDto(
                "Отправлено в RabbitMQ: " + request.value(),
                request.code(),
                request.timestamp()
        );
    }

//    public MessageDto send(MessageDto messageDto) {
//        LOG.log(Level.INFO,
//                String.format("response: value = %s, code = %s, timestamp = %s",
//                        messageDto.value(),
//                        messageDto.code(),
//                        messageDto.timestamp()));
//
//        return new MessageDto("Echo: " + messageDto.value(), messageDto.code(), messageDto.timestamp());
//    }
}
