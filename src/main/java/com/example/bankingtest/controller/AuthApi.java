package com.example.bankingtest.controller;


import com.example.bankingtest.models.Transactions;
import com.example.bankingtest.security.JwtTokenService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthApi {
    @Autowired
    private JwtTokenService tokenService;

    @RequestMapping("/structure")
    @Hidden
    public Object getStructure() {
        return new Transactions();
    }

    @RequestMapping(value = "/get-token", method = RequestMethod.POST)
    public Object Login() {
        return tokenService.genToken();
    }

}
