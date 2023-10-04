package com.example.Library.Management.System.model;

import com.example.Library.Management.System.Enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;

    String CardId;         // UUID

    @Enumerated(EnumType.STRING)
    CardStatus cardStatus;

    @CreationTimestamp
    Date dateOfIssue;

    @OneToOne
    @JoinColumn
    Student student;

    @OneToMany(mappedBy = "libraryCard", cascade = CascadeType.ALL)
    List<Transaction> transactions =new ArrayList<>();


}
