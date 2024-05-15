package com.yasiulevichnikita.VacationSolution.controllers;

import com.yasiulevichnikita.VacationSolution.models.User;
import com.yasiulevichnikita.VacationSolution.models.VacationRequest;
import com.yasiulevichnikita.VacationSolution.models.enums.Role;
import com.yasiulevichnikita.VacationSolution.services.UserService;
import com.yasiulevichnikita.VacationSolution.services.VacationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final VacationRequestService vacationRequestService;

    @GetMapping("/admin")
    public String admin(@RequestParam(name = "status", required = false) String status, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("requests", vacationRequestService.getAllRequests(status));
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("user") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRole(user, form);
        return "redirect:/admin";

    }

    @PostMapping("/admin/vacation-request/approve/{id}")
    public String vacationRequestApprove(@PathVariable("id") Long id) {
        vacationRequestService.approveRequest(id);
        return "redirect:/admin";
    }
}
