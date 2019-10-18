package com.vn.service;

import com.vn.jpa.VnpayTransactionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VnpayTransactionInfoService {

    Page<VnpayTransactionInfo> findAll(Pageable pageable);

    VnpayTransactionInfo insert(VnpayTransactionInfo vnpayTransactionInfo);

    VnpayTransactionInfo update(VnpayTransactionInfo vnpayTransactionInfo);

    void delete(VnpayTransactionInfo vnpayTransactionInfo);

    VnpayTransactionInfo findOne(Long id);

    VnpayTransactionInfo findByCode(String code);
}
