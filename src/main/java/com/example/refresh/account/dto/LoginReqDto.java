package com.example.refresh.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;
    @NotBlank
    private String pw;

    private String pwck;

}