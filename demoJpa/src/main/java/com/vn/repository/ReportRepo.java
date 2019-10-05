package com.vn.repository;

import com.vn.jpa.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "reportRepo")
public interface ReportRepo extends JpaRepository<Report, Long> {
}
