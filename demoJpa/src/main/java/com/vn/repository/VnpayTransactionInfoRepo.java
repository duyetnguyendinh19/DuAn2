package com.vn.repository;

import com.vn.jpa.VnpayTransactionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "vnpayTransactionRepo")
public interface VnpayTransactionInfoRepo extends JpaRepository<VnpayTransactionInfo, Long> {

    VnpayTransactionInfo findByCode(String code);

}
