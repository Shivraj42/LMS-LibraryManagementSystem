package com.example.Library.Management.System.model;

import com.example.Library.Management.System.Enums.TransactionStatus;
import com.example.Library.Management.System.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transaction")
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String transactionId;   // UUID
    @Enumerated(EnumType.STRING)
    TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    double fine;

    @CreationTimestamp
    Date transactionTime;

    @UpdateTimestamp
    Date updateTime;

    @ManyToOne
    @JoinColumn
    Book book;

    @ManyToOne
    @JoinColumn
    LibraryCard libraryCard;

}
