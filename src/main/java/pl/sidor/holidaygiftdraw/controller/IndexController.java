package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sidor.holidaygiftdraw.model.dto.UserRegistrationRequest;
import pl.sidor.holidaygiftdraw.service.AccountService;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    @Autowired
    private String myName;

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("myName", myName);

        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/register")
    public String registrationForm() {
        return "registration-form";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid UserRegistrationRequest request,
                           BindingResult bindingResult) {
        if (!request.arePasswordsEqual()) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "registration-form";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getFieldError().getDefaultMessage());
            return "registration-form";
        }
        if (!accountService.register(request)) {
            model.addAttribute("errorMessage", "This username is already taken.");
            return "registration-form";
        }
        return "redirect:/login";
    }

    @GetMapping("/register/{UUID}")
    public String registrationFormWithUUID() {
        return "registration-form";
    }

    @PostMapping("/register/{UUID}")
    public String registerWithUUID(@RequestParam String UUID, Model model, @Valid UserRegistrationRequest request,
                                   BindingResult bindingResult){

        register(model, request, bindingResult);


        return "redirect:/login";
    }
}