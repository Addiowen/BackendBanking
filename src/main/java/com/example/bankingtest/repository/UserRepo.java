package com.example.bankingtest.repository;


import com.example.bankingtest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String>  {



}
