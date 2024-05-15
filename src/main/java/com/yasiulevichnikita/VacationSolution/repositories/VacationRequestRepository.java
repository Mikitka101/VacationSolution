package com.yasiulevichnikita.VacationSolution.repositories;

import com.yasiulevichnikita.VacationSolution.models.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    List<VacationRequest> findByStatus(String status);
}
