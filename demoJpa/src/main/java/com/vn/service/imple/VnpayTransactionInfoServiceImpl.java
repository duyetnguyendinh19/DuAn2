package com.vn.service.imple;

import com.vn.jpa.VnpayTransactionInfo;
import com.vn.repository.VnpayTransactionInfoRepo;
import com.vn.service.VnpayTransactionInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service(value = "vnpayTransactionService")
@Transactional
public class VnpayTransactionInfoServiceImpl implements VnpayTransactionInfoService {

    @Resource
    private VnpayTransactionInfoRepo vnpayTransactionInfoRepo;

    @Override
    public Page<VnpayTransactionInfo> findAll(Pageable pageable) {
        return vnpayTransactionInfoRepo.findAll(pageable);
    }

    @Override
    public VnpayTransactionInfo insert(VnpayTransactionInfo vnpayTransactionInfo) {
        return vnpayTransactionInfoRepo.save(vnpayTransactionInfo);
    }

    @Override
    public VnpayTransactionInfo update(VnpayTransactionInfo vnpayTransactionInfo) {
        return vnpayTransactionInfoRepo.save(vnpayTransactionInfo);
    }

    @Override
    public void delete(VnpayTransactionInfo vnpayTransactionInfo) {
        vnpayTransactionInfoRepo.delete(vnpayTransactionInfo);
    }

    @Override
    public VnpayTransactionInfo findOne(Long id) {
        return vnpayTransactionInfoRepo.findOne(id);
    }

    @Override
    public VnpayTransactionInfo findByCode(String code) {
        return vnpayTransactionInfoRepo.findByCode(code);
    }
}
