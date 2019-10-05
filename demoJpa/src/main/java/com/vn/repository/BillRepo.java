package com.vn.repository;

import com.vn.jpa.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "billRepo")
public interface BillRepo extends JpaRepository<Bill, Long> {
}
