package com.vn.service;

import com.vn.jpa.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ReportService {

    Page<Report> findAll(Pageable pageable);

    Page<Report> findAllReportsReports(Date fromDate, Date toDate, String name, Pageable pageable);

    Report insert(Report report);

    Report update(Report report);

    void delete(Report report);

    Report findOne(Long id);
}
