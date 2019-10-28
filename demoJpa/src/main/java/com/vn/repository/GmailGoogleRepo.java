package com.vn.repository;

import com.vn.jpa.GmailGoogle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "gmailGoogleRepo")
public interface GmailGoogleRepo extends JpaRepository<GmailGoogle, String> {

}
