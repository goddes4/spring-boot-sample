package taeyo.sample.web;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import taeyo.sample.domain.Account;
import taeyo.sample.dto.AccountDTO;
import taeyo.sample.exception.BasicErrorResponse;
import taeyo.sample.repository.AccountRepository;
import taeyo.sample.service.AccountService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tykim on 2015-05-28.
 */
@Controller
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDTO.Request request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new BasicErrorResponse("account.post.error", "invalid input parameter"), HttpStatus.BAD_REQUEST);
        }
        Account account = modelMapper.map(request, Account.class);
        Account newAccount = accountService.createAccount(account);
        logger.info("account : {}", account);
        AccountDTO.Response response = modelMapper.map(newAccount, AccountDTO.Response.class);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    // /accounts?size=20&page=0&sort=username,asc&q=keesun
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccount(@RequestParam(required = false) String q, Pageable pageable) {
        Page<Account> accounts = accountService.page(q, pageable);

        List<AccountDTO.Response> contents = accounts.getContent().stream()
                .map(each -> modelMapper.map(each, AccountDTO.Response.class))
                .collect(Collectors.toList());

        PageImpl<AccountDTO.Response> result = new PageImpl<>(contents, pageable, accounts.getTotalElements());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public ResponseEntity getAccount(@PathVariable int id) {
        Account account = accountRepository.findOne(id);
        if (account == null) {
            return new ResponseEntity<>(new BasicErrorResponse("account.get.error", "user not found"),
                    HttpStatus.BAD_REQUEST);
        }
        AccountDTO.Response response = modelMapper.map(account, AccountDTO.Response.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@PathVariable int id, @RequestBody @Valid AccountDTO.Update request) {
        Account updateAccount = accountService.updateAccount(id, request);
        AccountDTO.Response response = modelMapper.map(updateAccount, AccountDTO.Response.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable int id) {
        Account account = accountRepository.findOne(id);
        if (account == null) {
            return new ResponseEntity<>(new BasicErrorResponse("account.get.error", "user not found"),
                    HttpStatus.BAD_REQUEST);
        }
        accountRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
