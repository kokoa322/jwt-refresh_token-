package com.example.refresh.account.service;

import com.example.refresh.account.dto.AccountReqDto;
import com.example.refresh.account.dto.LoginReqDto;
import com.example.refresh.account.entity.Account;
import com.example.refresh.account.entity.RefreshToken;
import com.example.refresh.account.repository.AccountRepository;
import com.example.refresh.account.repository.RefreshTokenRepository;
import com.example.refresh.global.dto.GlobalResDto;
import com.example.refresh.global.roles.RoleType;
import com.example.refresh.jwt.dto.TokenDto;
import com.example.refresh.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public GlobalResDto signup(AccountReqDto accountReqDto) {

        log.info("signup --> : {}", accountReqDto.getEmail());

        // nickname 중복검사
        if(accountRepository.findByEmail(accountReqDto.getNickname()).isPresent()){
            throw new RuntimeException("Overlap Check");
        }
        
        // 패스워드 암호화
        accountReqDto.setEncodePwd(passwordEncoder.encode(accountReqDto.getPw()));

        //roles_type 설정
        accountReqDto.setRoles(RoleType.USER.getCode());
        Account account = new Account(accountReqDto);

        // 회원가입 성공
        accountRepository.save(account);
        return new GlobalResDto("Success signup", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResDto login(LoginReqDto loginReqDto, HttpServletResponse response) {
        
        // 아이디 검사
        Account account = accountRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );
        
        // 비밀번호 검사
        if(!passwordEncoder.matches(loginReqDto.getPw(), account.getPw())) {
            throw new RuntimeException("Not matches Password");
        }
        
        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());
        
        // Refresh토큰 있는지 확인 
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(loginReqDto.getEmail());
        
        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);

        return new GlobalResDto("Success Login", HttpStatus.OK.value());
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    public void logout(LoginReqDto loginReqDto) {
        log.info("loginReqDto --> : {}", loginReqDto.toString());
        // 아이디 검사
        Account account = accountRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        // 비밀번호 검사
        if(!passwordEncoder.matches(loginReqDto.getPw(), account.getPw())) {
            throw new RuntimeException("Not matches Password");
        }
    }
}