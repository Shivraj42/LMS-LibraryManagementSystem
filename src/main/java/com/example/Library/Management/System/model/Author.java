package com.example.Library.Management.System.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "author")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    @Column(nullable = false, unique = true)
    String email;

    int age;

    @UpdateTimestamp
    Date lastActivity;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Book> books= new ArrayList<>();

}
