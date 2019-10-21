package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.AccountRole;
import pl.sidor.holidaygiftdraw.model.WishList;
import pl.sidor.holidaygiftdraw.model.dto.UserRegistrationRequest;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.AccountRoleRepository;
import pl.sidor.holidaygiftdraw.repository.WishListRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccountService  {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WishListRepository wishListRepository;

    @Value("${default.user.roles:USER}")
    private String[] defaultUserRegisterRoles;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void removeAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            if (!account.isAdmin()) {
                accountRepository.delete(account);
            }
        }
    }


    public boolean register(UserRegistrationRequest request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            return false;
        }

        Account account = new Account();
        WishList wishList = new WishList();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        wishList.setAccount(account);

        account.setRoles(findRolesByName(defaultUserRegisterRoles));

        accountRepository.save(account);
        wishList.setAccount(account);
        wishListRepository.save(wishList);

        return true;
    }

    public Set<AccountRole> findRolesByName(String... roles) {
        Set<AccountRole> accountRoles = new HashSet<>();
        for (String role : roles) {
            accountRoleRepository.findByName(role).ifPresent(accountRoles::add);
        }
        return accountRoles;
    }

    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    public void updateRoles(Long accountId, HttpServletRequest request) {
        // klucz w mapie to nazwa parametru
        // wartość to tablica, gdzie 0 element to wartość pola
        // accountId -> String[] {"2", "2"}
        // ADMIN -> String[] {"on"}
        // USER -> String[] {"on"} (jeśli nie będzie zaznaczony to nie wystąpi w mapie)

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<AccountRole> accountRoles = new HashSet<>();

            for (String key : parameterMap.keySet()) {
                accountRoleRepository.findByName(key).ifPresent(accountRoles::add);
            }

            account.setRoles(accountRoles);
            accountRepository.save(account);
        }
    }
}
