package com.khsa.usermanagement.domain.model.analytic;

import com.khsa.usermanagement.domain.dto.TransactionInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document(collection = "transactions")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1154290096740125485L;

    @Id
    private String id;

//    @ManyToOne
//    @JoinField(name = "account_id")
//    private Account account;

    @Field(name = "account_id")
    private Long accountId;

    @Field(name = "bucket_start_date")
    private LocalDateTime bucketStartDate;

    @Field(name = "bucket_end_date")
    private LocalDateTime bucketEndDate;

    @Field(name = "transaction_count")
    private Long transactionCount;

    @Field(name = "transactions")
    private List<TransactionInfo> transactions;

    public BigDecimal getTransactionTotal() {
        return transactions.stream().map(TransactionInfo::getTotal).reduce(BigDecimal::add).orElse(null);
    }
}
