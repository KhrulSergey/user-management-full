package com.khsa.usermanagement.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionInfo {

    private Long amount;

    private LocalDateTime date;

    private BigDecimal price;

    private String symbol;

    private BigDecimal total;

    private String transaction_code;
}
