package io.zact.zops.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.zact.zops.dto.NotificationEmailDTO;
import io.zact.zops.dto.NotificationEmailMapper;
import io.zact.zops.exception.NotificationEmailSaveException;
import io.zact.zops.exception.NotificationEmailSendingException;
import io.zact.zops.model.NotificationEmail;
import io.zact.zops.repository.NotificationEmailRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class NotificationEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEmailService.class);
    NotificationEmailRepository notificationEmailRepository;
    ReactiveMailer reactiveMailer;

    @Inject
    public NotificationEmailService(NotificationEmailRepository notificationEmailRepository, ReactiveMailer reactiveMailer) {
        this.notificationEmailRepository = notificationEmailRepository;
        this.reactiveMailer = reactiveMailer;
    }

    public void sendEmail(NotificationEmailDTO notificationEmailDTO){
        LOGGER.info("Construindo o modelo de E-mail");
        Mail mail = Mail.withText(notificationEmailDTO.getRecipient(), notificationEmailDTO.getSubject(), notificationEmailDTO.getBody());

        LOGGER.info("Verificando se a copias a serem enviadas");
        if (notificationEmailDTO.getCcRecipients() != null) {
            for (String ccRecipient : notificationEmailDTO.getCcRecipients()) {
                mail = mail.addCc(ccRecipient);
            }
        }

        try{
            reactiveMailer.send(mail)
                    .subscribe().with(
                            success -> LOGGER.info("E-mail enviado com sucesso para: " + notificationEmailDTO.getRecipient()),
                            failure -> LOGGER.error("Erro ao enviar e-mail: " + failure.getMessage()));
        } catch (Exception e){
            LOGGER.error("Erro ao enviar e-mail: " + e.getMessage(), e);
            throw new NotificationEmailSendingException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    public NotificationEmailDTO createNotificationEmail(NotificationEmailDTO notificationEmailDTO){
        LOGGER.info(notificationEmailDTO.isReceiveEmail() +" "+ notificationEmailDTO.getRecipient() + " " + notificationEmailDTO.getSubject());
        if(!notificationEmailDTO.isReceiveEmail() || notificationEmailDTO.getRecipient().isEmpty()){
            LOGGER.error("ReceiveEmail or Recipient is invalid or blank!");
            throw new NotificationEmailSaveException("ReceiveEmail or Recipient is invalid or blank!");
        }
        NotificationEmail NotificationEmail = NotificationEmailMapper.toEntity(notificationEmailDTO);
        try{
            notificationEmailRepository.persist(NotificationEmail);
            LOGGER.info("Notification Email persisting successfully");
        }catch(Exception e){
            LOGGER.error("Error saving information {}", e.getMessage());
            throw new NotificationEmailSaveException("Error saving information " + e.getMessage());
        }
        return NotificationEmailMapper.toDTO(NotificationEmail);
    }
}
