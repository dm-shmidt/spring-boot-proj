package com.test.quotation.model.request;

import jakarta.validation.constraints.NotNull;

public record IdRequest(
        @NotNull
        Long id
) {
}
