package com.vn.repository;

import com.vn.jpa.Infomation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "infomationRepo")
public interface InfomationRepo extends JpaRepository<Infomation, Long> {

    Infomation findByAuthUserId(Long id);

    Infomation findByPhone(String phone);
    
}
