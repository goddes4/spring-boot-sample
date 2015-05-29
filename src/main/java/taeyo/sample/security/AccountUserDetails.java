package taeyo.sample.security;

import taeyo.sample.domain.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tykim on 2015-05-29.
 */
public class AccountUserDetails extends User {

    @Getter
    private final Account account;

    public AccountUserDetails(Account account) {
        super(account.getUsername(), account.getPassword(), authorities(account.getRole()));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Account.Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role == Account.Role.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

}
