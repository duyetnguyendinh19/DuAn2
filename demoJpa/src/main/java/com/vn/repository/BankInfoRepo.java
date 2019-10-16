package com.vn.repository;

import com.vn.jpa.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "bankInfoRepo")
public interface BankInfoRepo extends JpaRepository<BankInfo, Long> {

    List<BankInfo> findAllByType(Integer type);

}
