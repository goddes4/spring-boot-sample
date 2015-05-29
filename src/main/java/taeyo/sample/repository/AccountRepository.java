package taeyo.sample.repository;

import taeyo.sample.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tykim on 2015-05-29.
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

    Page<Account> findByUsernameContains(String q, Pageable pageable);
}
