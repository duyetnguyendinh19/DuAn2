package com.vn.service.imple;

import com.vn.jpa.Report;
import com.vn.repository.ReportRepo;
import com.vn.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service(value = "reportService")
@Transactional
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportRepo reportRepo;

    @Override
    public Page<Report> findAll(Pageable pageable) {
        return reportRepo.findAll(pageable);
    }

    @Override
    public Report insert(Report report) {
        return reportRepo.save(report);
    }

    @Override
    public Report update(Report report) {
        return reportRepo.save(report);
    }

    @Override
    public void delete(Report report) {
        reportRepo.delete(report);
    }

    @Override
    public Report findOne(Long id) {
        return reportRepo.findOne(id);
    }
}
