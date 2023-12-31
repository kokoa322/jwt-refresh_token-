package com.example.refresh.account.controller;

import com.example.refresh.account.dto.AccountReqDto;
import com.example.refresh.account.dto.LoginReqDto;
import com.example.refresh.account.service.AccountService;
import com.example.refresh.global.dto.GlobalResDto;
import com.example.refresh.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public GlobalResDto signup (@RequestBody AccountReqDto accountReqDto){

        return accountService.signup(accountReqDto);
    }

    @PostMapping("/login")
    public GlobalResDto login (@RequestBody LoginReqDto loginReqDto, HttpServletResponse response){

        return accountService.login(loginReqDto, response);
    }

    @PostMapping("/logout")
    public void logout (@RequestBody LoginReqDto loginReqDto){
        log.info("loginReqDto.toString() --> : {}", loginReqDto.toString());
        accountService.logout(loginReqDto);
    }

    @PostMapping("/test1")
    public void test1 (@RequestBody LoginReqDto loginReqDto, @AuthenticationPrincipal
    UserDetailsImpl userDetails){
        log.info("/test1 --> : loginReqDto.toString() --> : {}", loginReqDto.toString());
        log.info("/test1 --> : userDetails.getUsername() --> : {}", userDetails.getUsername());
    }
}
