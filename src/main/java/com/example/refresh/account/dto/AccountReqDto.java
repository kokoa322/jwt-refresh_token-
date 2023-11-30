package com.example.refresh.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AccountReqDto {

    @NotBlank
    private String nickname;
    @NotBlank
    private String pw;
    @NotBlank
    private String email;
    private String pwck;
    @NotBlank
    private String roles;


    public AccountReqDto(String nickname, String pw, String pwck, String email, String roles) {
        this.nickname = nickname;
        this.pw = pw;
        this.pwck = pwck;
        this.email = email;
        this.roles = roles;
    }

    public void setEncodePwd(String encodePwd) {
        this.pw = encodePwd;
    }
}