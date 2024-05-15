package com.yasiulevichnikita.VacationSolution.services;

import com.yasiulevichnikita.VacationSolution.models.User;
import com.yasiulevichnikita.VacationSolution.models.VacationRequest;
import com.yasiulevichnikita.VacationSolution.repositories.UserRepository;
import com.yasiulevichnikita.VacationSolution.repositories.VacationRequestRepository;
import com.yasiulevichnikita.VacationSolution.utils.StatusUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<VacationRequest> getRequests(String status, User user) {
        if (status == null || status.equals("All requests")) {
            return vacationRequestRepository.findAll().stream()
                    .filter(request -> request.getUser().equals(user))
                    .toList();
        }
        return vacationRequestRepository.findByStatus(status).stream()
                .filter(request -> request.getUser().equals(user))
                .toList();
    }

    public List<VacationRequest> getAllRequests(String status) {
        if (status == null || status.equals("All requests")) {
            return vacationRequestRepository.findAll();
        }
        return vacationRequestRepository.findByStatus(status);
    }

    public void addRequest(Principal principal, VacationRequest request) {
        request.setUser(getUserByPrincipal(principal));
        log.info("Adding new request: {} - {}: {}", request.getStartDate(), request.getEndDate(), request.getComment());
        vacationRequestRepository.save(request);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteRequest(long id) {
        vacationRequestRepository.deleteById(id);
    }

    public VacationRequest getVacationRequestById(long id) {
        return vacationRequestRepository.findById(id).orElse(null);
    }

    public void approveRequest(Long id) {
        VacationRequest vacationRequest = vacationRequestRepository
                .findById(id)
                .orElse(null);
        if (vacationRequest != null) {
            switch (vacationRequest.getStatus()) {
                case StatusUtil.IN_PROGRESS -> {
                    vacationRequest.setStatus(StatusUtil.APPROVED);
                    log.info("Approve vacation request with id: {}", vacationRequest.getId());
                    sendStatusChangeInfoToEmail(vacationRequest.getUser(), vacationRequest, StatusUtil.APPROVED);
                }
                case StatusUtil.APPROVED -> {
                    vacationRequest.setStatus(StatusUtil.REJECTED);
                    log.info("Reject vacation request with id: {}", vacationRequest.getId());
                    sendStatusChangeInfoToEmail(vacationRequest.getUser(), vacationRequest, StatusUtil.REJECTED);
                }
                default -> {
                    vacationRequest.setStatus(StatusUtil.IN_PROGRESS);
                    log.info("Set vacation request with id \"In Progress\" status: {}", vacationRequest.getId());
                    sendStatusChangeInfoToEmail(vacationRequest.getUser(), vacationRequest, StatusUtil.IN_PROGRESS);
                }
            }
            vacationRequestRepository.save(vacationRequest);
        }
    }

    public void sendStatusChangeInfoToEmail(User user, VacationRequest request, String status) {

        //Записал в переменные из-за java.sql.SQLException
        String name = user.getName();
        String reqDate = request.getRequestDate();
        String email = user.getEmail();
        long id = request.getId();


        new Thread(() -> {
            try {
                if(status.equals(StatusUtil.APPROVED)) {
                    emailService.sendApproveEmailWithAttachment(user, request);
                } else {
                    emailService.sendStatusEmail(user.getEmail(), "Vacation request status", name, reqDate, status);
                }
                log.info("Email sent to {} about request with id: {}", email, id);
            } catch (Exception e) {
                log.error("Error sending email: {}", e.getMessage());
            }
        }).start();
    }
}
