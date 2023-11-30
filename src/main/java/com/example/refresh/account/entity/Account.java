package com.example.refresh.account.entity;

import com.example.refresh.account.dto.AccountReqDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
    @NotBlank
    private String pw;
    @NotBlank
    private String roles;
    

    public Account(AccountReqDto accountReqDto) {
        this.nickname = accountReqDto.getNickname();
        this.pw = accountReqDto.getPw();
        this.email = accountReqDto.getEmail();
        this.roles = accountReqDto.getRoles();
    }
}