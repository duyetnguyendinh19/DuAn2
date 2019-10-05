package com.vn.repository;

import com.vn.jpa.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "historyRepo")
public interface HistoryRepo extends JpaRepository<History, Long> {
}
