package net.javaguides.springboot_backend.dto;

public record UserDTO (
        int id,
        String name,
        String email,
        String type) {
}