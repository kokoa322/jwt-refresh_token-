package com.example.refresh.security.user;

import com.example.refresh.account.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDetailsImpl implements UserDetails {

    private Account account;

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
            this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Map<String, GrantedAuthority> authorities = new HashMap<>();
        authorities.put("ROLE_USER", new SimpleGrantedAuthority("ROLE_USER"));
        return authorities.values();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
