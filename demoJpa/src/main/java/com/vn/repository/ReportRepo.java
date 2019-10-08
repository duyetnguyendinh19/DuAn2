package com.vn.repository;

import com.vn.jpa.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository(value = "reportRepo")
public interface ReportRepo extends JpaRepository<Report, Long> {

    @Query(value = "SELECT rp FROM Report rp WHERE (rp.createdDate BETWEEN :fromDate AND :toDate) "
            + "AND (:name IS NULL OR :name = '' OR rp.name LIKE CONCAT('%', :name, '%'))")
    Page<Report> findAllReportsReports(@Param("fromDate") Date fromDate,
                                       @Param("toDate") Date toDate,
                                       @Param("name") String name,
                                       Pageable pageable);

}
