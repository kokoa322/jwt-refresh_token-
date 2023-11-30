package com.example.refresh.security.user;

import com.example.refresh.account.entity.Account;
import com.example.refresh.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
// userDetailsImple에 account를 넣어주는 서비스입니다.
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Account account = accountRepository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("Not Found Account")
		);
		log.info("account.toString() -- > : {}", account.toString());
		UserDetailsImpl userDetails = new UserDetailsImpl();
		userDetails.setAccount(account);

		return userDetails;
	}
}