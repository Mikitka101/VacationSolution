package com.yasiulevichnikita.VacationSolution.controllers;

import com.yasiulevichnikita.VacationSolution.models.VacationRequest;
import com.yasiulevichnikita.VacationSolution.services.VacationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class VacationRequestController {
    private final VacationRequestService vacationRequestService;

    @GetMapping("/vacation-requests")
    public String getRequests(@RequestParam(name = "status", required = false) String status, Principal principal, Model model) {
        model.addAttribute("requests", vacationRequestService.getRequests(status, vacationRequestService.getUserByPrincipal(principal)));
        model.addAttribute("user", vacationRequestService.getUserByPrincipal(principal));
        return "vacation-requests";
    }

    @GetMapping("/vacation-request/{id}")
    public String vacationRequestInfo(@PathVariable Long id, Model model) {
        model.addAttribute("vacationRequest", vacationRequestService.getVacationRequestById(id));
        return "request-info";
    }

    @PostMapping("/vacation-request/create")
    public String createVacationRequest(VacationRequest vacationRequest, Principal principal) {
        vacationRequestService.addRequest(principal, vacationRequest);
        return "redirect:/vacation-requests";
    }

    @PostMapping("/vacation-request/delete/{id}")
    public String deleteVacationRequest(@PathVariable Long id) {
        vacationRequestService.deleteRequest(id);
        return "redirect:/vacation-requests";
    }



}
