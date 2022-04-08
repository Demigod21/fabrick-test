package com.davide.falcone.fabricktest.repo;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionDTO,Integer> {

}