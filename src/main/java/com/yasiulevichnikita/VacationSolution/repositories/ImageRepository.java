package com.yasiulevichnikita.VacationSolution.repositories;

import com.yasiulevichnikita.VacationSolution.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
