package com.gerenciadordepessoas.aplicativo.repository;

import com.gerenciadordepessoas.aplicativo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
