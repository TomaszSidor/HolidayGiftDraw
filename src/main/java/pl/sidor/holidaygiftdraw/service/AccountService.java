package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.AccountRole;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.model.dto.UserRegistrationRequest;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.AccountRoleRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

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
    private HolidayEventService holidayEventService;
    @Autowired
    private HolidayEventRepository holidayEventRepository;

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

    public boolean register(UserRegistrationRequest request, String eventUUID) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            return false;
        }
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRoles(findRolesByName(defaultUserRegisterRoles));
        accountRepository.save(account);
        if(eventUUID!=null){
            Optional<HolidayEvent> holidayEventOptional = holidayEventService.findByIdentifier(eventUUID);
            if (holidayEventOptional.isPresent()) {
                HolidayEvent holidayEvent = holidayEventOptional.get();
                holidayEvent.getAccountSet().add(account);
                holidayEventRepository.save(holidayEvent);
            }


        }


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

    public Optional<Account> getByUsername(String username) {
        return accountRepository.findByUsername(username);
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
