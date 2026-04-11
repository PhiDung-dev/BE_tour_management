package com.example.BE_tour_management.repository;

import com.example.BE_tour_management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    boolean existsByUsername(String username);
    Account findByUsername(String username);

}
