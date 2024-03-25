package com.example.bankingtest.service;


import com.example.bankingtest.models.Accounts;
import com.example.bankingtest.models.ProcessingResp;
import com.example.bankingtest.models.Transactions;
import com.example.bankingtest.models.User;
import com.example.bankingtest.repository.AccountRepo;
import com.example.bankingtest.repository.TransactionRepo;
import com.example.bankingtest.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Userservice {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AccountRepo AccRepo;

    @Autowired
    private TransactionRepo transact;


    @Transactional
    public Object Registration(User user) {
        user.setPIN(((int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000)) + "");

        Accounts accounts = new Accounts();
        accounts.setId(((int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000)) + "");
        accounts.setUserID(user.getId());
        accounts.setName(user.getName());
        accounts.setDebit(0);
        accounts.setCredit(0);
        accounts.setBalance(0);

        AccRepo.save(accounts);
        user.setUserAccount(accounts.getId());
        repo.save(user);

        return ProcessingResp.builder()
                .status("SUCCESS")
                .responseMessage("successful")
                .responseCode("000")
                .user(user)
                .build();

    }

    public User getUserUsingId(String id) {
        return repo.findById(id).orElse(null);
    }


    public Accounts getAccountUsingId(String id) {
        return AccRepo.findById(id).orElse(new Accounts());
    }

    public Transactions CreditAccount(Transactions transactions) {

        return transact.save(transactions);
    }

}
