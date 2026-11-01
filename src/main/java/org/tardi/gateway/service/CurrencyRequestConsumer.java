package org.tardi.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.tardi.gateway.model.records.CurrencyRequest;
import org.tardi.gateway.model.records.CurrencyResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class CurrencyRequestConsumer {

    @Inject
    CurrencyService currencyService;

    @Inject
    @Channel("currency-replies")
    Emitter<CurrencyResponse> replyEmitter;

    private static final Logger LOG = Logger.getLogger(String.valueOf(CurrencyRequestConsumer.class));


    @Incoming("currency-requests")
    public Uni<Void> process(Message<Buffer> message) {
        try {

            LOG.log(Level.INFO, "=== ПОЛУЧЕНО СООБЩЕНИЕ В QUARKUS ===");

            // Получаем JsonObject
            String jsonString = message.getPayload().toString();
            LOG.log(Level.INFO, "Сырая строка: " + jsonString);

            // Вручную преобразуем в ваш рекорд
            ObjectMapper mapper = new ObjectMapper();
            CurrencyRequest request = mapper.readValue(jsonString, CurrencyRequest.class);
            LOG.log(Level.INFO, "Распарсено: " + request.toString());


//            String replyTo = message.getMetadata(IncomingRabbitMQMetadata.class)
//                    .flatMap(IncomingRabbitMQMetadata::getReplyTo)
//                    .orElse(null);

//            if (replyTo != null) {
//                CurrencyResponse response = currencyService.getRate(request.targetCurrency(), request.date());
//
//                OutgoingRabbitMQMetadata metadata = new OutgoingRabbitMQMetadata.Builder()
//                        .withRoutingKey(replyTo)
//                        .build();
//
//                replyEmitter.send(Message.of(response, Metadata.of(metadata)));
//            }

            CurrencyResponse response = currencyService.getRate(request.targetCurrency(), request.date());

            LOG.log(Level.INFO, "Респонс: " + response.toString());


//            replyEmitter.send(response);


            // Возвращаем Uni<Void>, чтобы подтвердить обработку сообщения
            return Uni.createFrom().voidItem();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage(), e);

            return Uni.createFrom().failure(e);
        }

    }
}