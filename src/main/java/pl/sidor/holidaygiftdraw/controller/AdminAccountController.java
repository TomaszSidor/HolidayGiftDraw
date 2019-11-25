package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.service.AccountRoleService;
import pl.sidor.holidaygiftdraw.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin/account/")
//  @PreAuthorize(value = "hasRole('ADMIN')")
public class AdminAccountController {

    @ModelAttribute("currentUsername")
    public String currentUsername(@AuthenticationPrincipal Principal principal) {
        if (principal != null){

            return  principal.getName();
        }
        return "no name";

    }

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRoleService accountRoleService;

    @GetMapping("/list")
    public String getUserList(Model model) {
        List<Account> accountList = accountService.getAll();
        model.addAttribute("accounts", accountList);
        return "account-list";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("accountId") Long id) {
        accountService.removeAccount(id);

        return "redirect:/admin/account/list";
    }

    @GetMapping("/editRoles")
    public String editRoles(Model model, @RequestParam("accountId") Long id) {
        Optional<Account> accountOptional = accountService.getById(id);
        if (accountOptional.isPresent()) {
            model.addAttribute("roles", accountRoleService.getAll());
            model.addAttribute("user", accountOptional.get());

            return "account-roles";
        }
        return "redirect:/admin/account/list";
    }

    @PostMapping("/editRoles")
    public String editRoles(Long accountId, HttpServletRequest request) {
        accountService.updateRoles(accountId, request);

        return "redirect:/admin/account/list";
    }

}
