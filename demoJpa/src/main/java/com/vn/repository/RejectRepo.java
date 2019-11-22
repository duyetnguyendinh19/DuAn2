package com.vn.repository;

import com.vn.jpa.Reject;
import com.vn.model.ChartDashboardBillOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "rejectRepo")
public interface RejectRepo extends JpaRepository<Reject, Long> {

    @Query(value = "SELECT r.created_date, COUNT(id) FROM reject r " +
            "WHERE (r.created_date BETWEEN DATE(:fromDate) AND DATE(:toDate))" +
            "GROUP BY DATE(r.created_date)", nativeQuery = true)
    List<Object[]> listCountRejectDashBoard(@Param("fromDate") Date fromDate,
                                            @Param("toDate") Date toDate);

}
