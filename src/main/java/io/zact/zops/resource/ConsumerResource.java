package io.zact.zops.resource;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.zact.zops.dto.NotificationEmailDTO;
import io.zact.zops.service.NotificationEmailService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

public class ConsumerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerResource.class);

    NotificationEmailService notificationEmailService;

    @Inject
    public ConsumerResource(NotificationEmailService notificationEmailService) {
        this.notificationEmailService = notificationEmailService;
    }

    @Incoming("sendEmail")
    public void processNotificationEmail(NotificationEmailDTO notificationEmailDTO){
        LOGGER.info("Starting persist notification email");
        NotificationEmailDTO newNotificationEmailDTO = notificationEmailService.createNotificationEmail(notificationEmailDTO);
        if (notificationEmailDTO.isReceiveEmail()) {
            LOGGER.info("Received email and prepared to send notification");
            notificationEmailService.sendEmail(newNotificationEmailDTO);
        }
    }
}
