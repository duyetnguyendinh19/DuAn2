package com.vn.service.imple;

import com.vn.jpa.Infomation;
import com.vn.repository.InfomationRepo;
import com.vn.service.InfomationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service(value = "infomationService")
@Transactional
public class InfomationServiceImpl implements InfomationService {

    @Resource
    private InfomationRepo infomationRepo;

    @Override
    public Page<Infomation> findAll(Pageable pageable) {
        return infomationRepo.findAll(pageable);
    }

    @Override
    public Infomation create(Infomation infomation) {
        return infomationRepo.save(infomation);
    }

    @Override
    public Infomation update(Infomation infomation) {
        return infomationRepo.save(infomation);
    }

    @Override
    public void delete(Infomation infomation) {
        infomationRepo.delete(infomation);
    }

    @Override
    public Infomation findOne(Long id) {
        return infomationRepo.findOne(id);
    }

    @Override
    public Infomation findByAuthUserId(Long id) {
        return infomationRepo.findByAuthUserId(id);
    }

	@Override
	public Infomation findByPhone(String phone){
		return infomationRepo.findByPhone(phone);
	}
}
