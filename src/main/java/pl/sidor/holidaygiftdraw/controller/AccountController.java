package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.service.AccountService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private  AccountService accountService;

    @ModelAttribute("currentUsername")
    public String currentUsername(@AuthenticationPrincipal Principal principal) {
        if (principal != null){

            return  principal.getName();
        }
        return "no name";

    }


    @GetMapping("")
    public String getAccountByUserId (Principal principal, Model model) {
        Long id = accountService.getByUsername(principal.getName()).get().getId();
        Optional<Account> accountOptional = accountService.getById(id);

        model.addAttribute("account", accountOptional.get());

        return "account";
    }

}
