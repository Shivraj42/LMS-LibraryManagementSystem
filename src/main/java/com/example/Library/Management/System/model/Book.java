package com.example.Library.Management.System.model;

import com.example.Library.Management.System.Enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name ="book")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;

    @Enumerated(EnumType.STRING)
    Genre genre;

    int noOfPages;

    Double cost;

    @ManyToOne
    @JoinColumn
    Author author;

}
