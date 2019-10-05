package com.vn.service;

import com.vn.jpa.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryService {

    Page<History> findAll(Pageable pageable);

    History insert(History history);

    History update(History history);

    void delete(History history);

    History findOne(Long id);
}
