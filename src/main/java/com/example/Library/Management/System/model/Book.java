package com.example.Library.Management.System.model;

import com.example.Library.Management.System.Enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name ="book")
@Builder
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;

    @Enumerated(EnumType.STRING)
    Genre genre;

    int noOfPages;

    boolean issued;

    Double cost;

    @ManyToOne
    @JoinColumn
    Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<Transaction> transactions=new ArrayList<>();
}
