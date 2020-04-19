package com.petfishco.stocktaking.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String errorMessage;
    private int status;
    private String error;
    private String timestamp;
}
