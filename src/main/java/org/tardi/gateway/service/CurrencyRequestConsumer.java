package org.tardi.gateway.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.tardi.gateway.model.records.CurrencyRequest;
import org.tardi.gateway.model.records.CurrencyResponse;

import java.util.Arrays;
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
    public Uni<Void> process(Message<CurrencyRequest> message) {
        try {
            CurrencyRequest request = message.getPayload();

            LOG.log(Level.INFO, "=== ПОЛУЧЕНО СООБЩЕНИЕ В QUARKUS ===");
            LOG.log(Level.INFO, "Метаданные: " + message.getMetadata().toString());
            LOG.log(Level.INFO, "Processing MQ message. metadata: "
                    + message.getMetadata().toString() + "payload: " + message.getPayload().toString());

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
            LOG.log(Level.INFO, "Error in mq processing:" + Arrays.toString(e.getStackTrace()));

            return null;
        }

    }
}