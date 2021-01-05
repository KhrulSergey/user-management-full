package com.khsa.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class AdminPanelController {

    @Autowired
    public AdminPanelController() {
    }

    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }

    @GetMapping({"/homepage", "/home"})
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "homepage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    //    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) error){
//        ModelAndView modelAndView = new ModelAndView();
//        if (error != null) {
//            modelAndView.setViewName("error page");
//        } else modelAndView.setViewName("login");
//
//        return modelAndView;
//    }

    //    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/register")
    public String register() {
        return "redirect:/user/register";
    }
}
