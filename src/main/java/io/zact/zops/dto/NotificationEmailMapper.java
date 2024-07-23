package io.zact.zops.dto;

import io.zact.zops.model.NotificationEmail;

import java.util.ArrayList;
import java.util.List;

public class NotificationEmailMapper {
    public static NotificationEmail toEntity(NotificationEmailDTO notificationEmailDTO){
        return setNotificationEmailFromDTO(notificationEmailDTO);
    }

    public static NotificationEmailDTO toDTO(NotificationEmail notificationEmail){
        return setDTOFromNotificationEmail(notificationEmail);
    }

    public static List<NotificationEmailDTO> toListDTOs(List<NotificationEmail> notificationEmails){
        ArrayList<NotificationEmailDTO> notificationEmailDTOs = new ArrayList<NotificationEmailDTO>();
        for(NotificationEmail notificationEmail : notificationEmails){
            notificationEmailDTOs.add(toDTO(notificationEmail));
        }
        return notificationEmailDTOs;
    }

    private static NotificationEmailDTO setDTOFromNotificationEmail(NotificationEmail notificationEmail){
        NotificationEmailDTO newNotificationEmailDTO = new NotificationEmailDTO();
        newNotificationEmailDTO.setReceiveEmail(notificationEmail.isReceiveEmail());
        newNotificationEmailDTO.setSubject(notificationEmail.getSubject());
        newNotificationEmailDTO.setBody(notificationEmail.getBody());
        newNotificationEmailDTO.setSender(notificationEmail.getSender());
        newNotificationEmailDTO.setData(notificationEmail.getData());
        newNotificationEmailDTO.setRecipient(notificationEmail.getRecipient());
        newNotificationEmailDTO.setSubject(notificationEmail.getSubject());

        return newNotificationEmailDTO;
    }

    private static NotificationEmail setNotificationEmailFromDTO(NotificationEmailDTO notificationEmailDTO){
        NotificationEmail newNotificationEmail = new NotificationEmail();
        newNotificationEmail.setReceiveEmail(notificationEmailDTO.isReceiveEmail());
        newNotificationEmail.setBody(notificationEmailDTO.getBody());
        newNotificationEmail.setSubject(notificationEmailDTO.getSubject());
        newNotificationEmail.setSender(notificationEmailDTO.getSender());
        newNotificationEmail.setData(notificationEmailDTO.getData());
        newNotificationEmail.setCcRecipients(notificationEmailDTO.getCcRecipients());
        newNotificationEmail.setRecipient(notificationEmailDTO.getRecipient());

        return newNotificationEmail;
    }
}
