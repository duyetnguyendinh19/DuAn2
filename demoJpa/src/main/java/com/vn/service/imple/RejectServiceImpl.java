package com.vn.service.imple;

import com.vn.jpa.Reject;
import com.vn.repository.RejectRepo;
import com.vn.service.RejectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service(value = "rejectService")
@Transactional
public class RejectServiceImpl implements RejectService {

    @Resource
    private RejectRepo rejectRepo;

    @Override
    public Page<Reject> findAll(Pageable pageable) {
        return rejectRepo.findAll(pageable);
    }

    @Override
    public Reject insert(Reject reject) {
        return rejectRepo.save(reject);
    }

    @Override
    public Reject update(Reject reject) {
        return rejectRepo.save(reject);
    }

    @Override
    public void delete(Reject reject) {
        rejectRepo.delete(reject);
    }

    @Override
    public Reject findOne(Long id) {
        return rejectRepo.findOne(id);
    }
}
