package taeyo.sample.service;

import taeyo.sample.domain.Account;
import taeyo.sample.dto.AccountDTO;
import taeyo.sample.exception.AccountNotFoundException;
import taeyo.sample.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by tykim on 2015-05-29.
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        account.setCreatedAt(new Date());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Page<Account> page(String q, Pageable pageable) {
        Pageable pageRequest;
        if (pageable.getSort() == null) {
            pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), usernameASC());
        } else {
            pageRequest = pageable;
        }

        if (q == null) {
            return accountRepository.findAll(pageRequest);
        } else {
            return accountRepository.findByUsernameContains(q, pageRequest);
        }
    }

    private Sort usernameASC() {
        return new Sort(Sort.Direction.ASC, "username");
    }

    public Account updateAccount(int id, AccountDTO.Update request) {
        Account account = accountRepository.findOne(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        String username = request.getUsername();
        if (!StringUtils.isEmpty(username)) {
            account.setUsername(username);
        }
        String password = request.getPassword();
        if (!StringUtils.isEmpty(password)) {
            account.setPassword(passwordEncoder.encode(password));
        }
        return account;
    }
}
