package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.service.AccountService;

import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private  AccountService accountService;

    @GetMapping("")
    public String getAccount(Principal principal, Model model){
        model.addAttribute("account", accountService.getByUsername(principal.getName()).get());
        return "account";
    }

}
