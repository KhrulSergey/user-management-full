package com.khsa.usermanagement.controller;

import com.khsa.usermanagement.domain.model.User;
import com.khsa.usermanagement.domain.model.analytic.Account;
import com.khsa.usermanagement.domain.model.analytic.Customer;
import com.khsa.usermanagement.domain.model.analytic.Transaction;
import com.khsa.usermanagement.service.AccountService;
import com.khsa.usermanagement.service.CustomerService;
import com.khsa.usermanagement.service.TransactionService;
import com.khsa.usermanagement.service.UserService;
import com.khsa.usermanagement.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping(value = "/analytic")
public class AnalyticDataController {

    private TransactionService transactionService;
    private AccountService accountService;
    private CustomerService customerService;

    @Autowired
    public AnalyticDataController(TransactionService transactionService,
                                  AccountService accountService,
                                  CustomerService customerService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping("/transactions")
    public ModelAndView showTransactions(
            @AuthenticationPrincipal User user,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        ModelAndView model = new ModelAndView();
        final int currentPage = page.orElse(1);
        final int pageSize = size.orElse(5);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.getByUserName(username);
        if (!username.equals("anonymousUser") && nonNull(customer)) {
            List<Account> accounts = customer.getAccounts().stream()
                    .map(accountGUID -> accountService.getByGuid(accountGUID))
                    .collect(Collectors.toList());


//        SearchUtil.pageable(page - 1, size, direction, properties)
            Page<Transaction> transactionList = transactionService.list(
                    SearchUtil.pageable(currentPage - 1, pageSize, Sort.Direction.DESC, "property"),
                    accounts);

            if (!transactionList.isEmpty()) {
                model.addObject("transactionList", transactionList);
            }

            int totalPages = transactionList.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addObject("pageNumbers", pageNumbers);
            }
        }
        model.setViewName("analytic/transactions");
        return model;

    }
}
