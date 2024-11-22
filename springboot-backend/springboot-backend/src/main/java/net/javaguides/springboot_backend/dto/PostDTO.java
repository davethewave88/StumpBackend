package net.javaguides.springboot_backend.dto;

import java.sql.Timestamp;

public record PostDTO (
        int id,
        int creator_id,
        Timestamp create_time,
        String link) {
}