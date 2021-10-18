package com.gerenciadordepessoas.aplicativo.repository;

import com.gerenciadordepessoas.aplicativo.entity.Person;
import com.gerenciadordepessoas.aplicativo.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
