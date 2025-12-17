package org.example.javafullstacktraining.repository;

import org.example.javafullstacktraining.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExampleRepository extends JpaRepository<Student, Long> {
}
