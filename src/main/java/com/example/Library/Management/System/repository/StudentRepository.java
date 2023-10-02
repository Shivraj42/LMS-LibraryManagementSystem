package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.Enums.Gender;
import com.example.Library.Management.System.model.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public List<Student> findByGender(Gender gender);
}
