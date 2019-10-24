package com.vn.repository;

import com.vn.jpa.Reject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "rejectRepo")
public interface RejectRepo extends JpaRepository<Reject, Long> {
}
