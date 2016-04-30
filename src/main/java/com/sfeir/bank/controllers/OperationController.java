package com.sfeir.bank.controllers;

import com.google.common.collect.Lists;
import com.sfeir.bank.beans.OperationResult;
import com.sfeir.bank.beans.OperationType;
import com.sfeir.bank.beans.Request;
import com.sfeir.bank.services.AccountService;
import com.sfeir.bank.services.DetailService;
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

    @Autowired
    private DetailService detailService;

    @RequestMapping("/")
    public String home(Model model) {
        return displayMainPage(model);
    }

    private String displayMainPage(Model model) {
        model.addAttribute("balance", accountService.getBalance());
        model.addAttribute("operations", Lists.reverse(detailService.getOperations()));

        return "main";
    }

    @RequestMapping(value = "/deposite", method = RequestMethod.POST)
    public String saveMoney(@ModelAttribute Request request, Model model) {
        OperationResult result = operationService.save(request.getAmount());

        model.addAttribute("input", request.getAmount());
        model.addAttribute("operationType", OperationType.DEPOSITE);
        model.addAttribute("result", result);
        return displayMainPage(model);
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawMoney(@ModelAttribute Request request, Model model) {
        OperationResult result = operationService.withdraw(request.getAmount());

        model.addAttribute("input", request.getAmount());
        model.addAttribute("operationType", OperationType.WITHDRAW);
        model.addAttribute("result", result);
        return displayMainPage(model);
    }
}
