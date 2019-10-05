package com.vn.service;

import com.vn.jpa.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

    Page<Report> findAll(Pageable pageable);

    Report insert(Report report);

    Report update(Report report);

    void delete(Report report);

    Report findOne(Long id);
}
