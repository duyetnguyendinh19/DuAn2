package com.vn.service.imple;

import com.vn.jpa.History;
import com.vn.repository.HistoryRepo;
import com.vn.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service(value = "historyService")
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Resource
    private HistoryRepo historyRepo;

    @Override
    public Page<History> findAll(Pageable pageable) {
        return historyRepo.findAll(pageable);
    }

    @Override
    public History insert(History history) {
        return historyRepo.save(history);
    }

    @Override
    public History update(History history) {
        return historyRepo.save(history);
    }

    @Override
    public void delete(History history) {
        historyRepo.delete(history);
    }

    @Override
    public History findOne(Long id) {
        return historyRepo.findOne(id);
    }
}
