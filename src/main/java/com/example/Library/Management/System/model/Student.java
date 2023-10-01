package com.example.Library.Management.System.model;

import com.example.Library.Management.System.Enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.naming.Name;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "student_info")
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int regId;

    @Column(name="student_name")
    String name;

    int age;

    @Column( unique = true,nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    LibraryCard libraryCard;

}
