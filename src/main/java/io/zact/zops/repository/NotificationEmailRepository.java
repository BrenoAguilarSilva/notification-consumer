package io.zact.zops.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.zact.zops.model.NotificationEmail;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationEmailRepository implements PanacheMongoRepository<NotificationEmail> {
}
