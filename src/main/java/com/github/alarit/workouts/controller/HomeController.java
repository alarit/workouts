package com.github.alarit.workouts.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "index";
    }
}
