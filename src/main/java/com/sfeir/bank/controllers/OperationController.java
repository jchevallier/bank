package com.sfeir.bank.controllers;

import com.sfeir.bank.beans.OperationResult;
import com.sfeir.bank.beans.Request;
import com.sfeir.bank.services.AccountService;
import com.sfeir.bank.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("balance", accountService.getBalance());
        return "main";
    }

    @RequestMapping(value = "/deposite", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    public String saveMoney(@ModelAttribute Request request, Model model) {
        OperationResult result = operationService.save(request.getAmount());

        model.addAttribute("result", result);
        model.addAttribute("balance", accountService.getBalance());

        return "main";
    }
}
