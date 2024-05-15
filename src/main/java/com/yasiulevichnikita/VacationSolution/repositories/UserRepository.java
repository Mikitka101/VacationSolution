package com.yasiulevichnikita.VacationSolution.repositories;

import com.yasiulevichnikita.VacationSolution.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
