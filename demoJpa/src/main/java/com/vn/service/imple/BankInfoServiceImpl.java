package com.vn.service.imple;

import com.vn.jpa.BankInfo;
import com.vn.repository.BankInfoRepo;
import com.vn.service.BankInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service(value = "bankInfoService")
@Transactional
public class BankInfoServiceImpl implements BankInfoService {

    @Resource
    private BankInfoRepo bankInfoRepo;

    @Override
    public Page<BankInfo> findAll(Pageable pageable) {
        return bankInfoRepo.findAll(pageable);
    }

    @Override
    public BankInfo insert(BankInfo bankInfo) {
        return bankInfoRepo.save(bankInfo);
    }

    @Override
    public BankInfo update(BankInfo bankInfo) {
        return bankInfoRepo.save(bankInfo);
    }

    @Override
    public void delete(BankInfo bankInfo) {
        bankInfoRepo.delete(bankInfo);
    }

    @Override
    public BankInfo findOne(Long id) {
        return bankInfoRepo.findOne(id);
    }

    @Override
    public List<BankInfo> findAllByType(Integer type) {
        return bankInfoRepo.findAllByType(type);
    }

	@Override
	public List<BankInfo> findAll() {
		return bankInfoRepo.findAll();
	}
}
