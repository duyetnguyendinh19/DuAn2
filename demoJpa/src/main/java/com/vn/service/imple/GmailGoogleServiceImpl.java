package com.vn.service.imple;

import com.vn.jpa.GmailGoogle;
import com.vn.repository.GmailGoogleRepo;
import com.vn.service.GmailGoogleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service(value = "gmailGoogleService")
@Transactional
public class GmailGoogleServiceImpl implements GmailGoogleService {

    @Resource
    private GmailGoogleRepo gmailGoogleRepo;

    @Override
    public GmailGoogle insert(GmailGoogle gmailGoogle) {
        return gmailGoogleRepo.save(gmailGoogle);
    }
}
