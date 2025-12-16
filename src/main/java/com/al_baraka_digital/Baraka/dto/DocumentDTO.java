package com.al_baraka_digital.Baraka.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentDTO(
        UUID id,
        String fileName,
        String fileType,
        LocalDateTime uploadedAt
) {}
