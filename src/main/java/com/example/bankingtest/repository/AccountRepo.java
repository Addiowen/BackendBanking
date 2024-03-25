package com.example.bankingtest.repository;


import com.example.bankingtest.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Accounts,String> {

    @Query(value = "SELECT SUM(credit) FROM tbl_accounts WHERE id =:id", nativeQuery = true)
    Optional<Double> findCreditById(@Param("id") String id);

    @Query(value = "SELECT SUM(debit) FROM tbl_accounts WHERE id =:id", nativeQuery = true)
    Optional<Double>  findDebitById(@Param("id") String id);

    @Query(value = "SELECT * FROM tbl_accounts WHERE id =:id", nativeQuery = true)
    Optional<Accounts>  findCustomerBalanceById(@Param("id") String id);

}
