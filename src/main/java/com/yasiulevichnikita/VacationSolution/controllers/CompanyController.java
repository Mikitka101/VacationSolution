package com.yasiulevichnikita.VacationSolution.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {
    @GetMapping("/")
    public String companyInfo() {
        return "company-info";
    }
}