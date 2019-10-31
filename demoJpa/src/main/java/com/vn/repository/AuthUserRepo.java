package com.vn.repository;

import com.vn.jpa.AuthUser;
import com.vn.model.AuthUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "authUserRepo")
public interface AuthUserRepo extends JpaRepository<AuthUser, Long> {

    AuthUser findByUserName(String var);

    @Query(value = "SELECT u.id, u.userName, u.fullName, u.email, u.status, u.userType, u.createdDate "
            + " FROM AuthUser u WHERE (:userName IS NULL OR :userName = '' OR u.userName LIKE CONCAT('%', :userName, '%'))"
            + " AND (:email IS NULL OR :email = '' OR u.email = :email)", nativeQuery = false)
    Page<Object[]> listUsers(@Param(value = "userName") String userName, @Param(value = "email") String email,Pageable pageable);

    @Query(value = "SELECT u.id, u.userName, u.fullName, u.email, u.status, u.createdDate "
            + " FROM AuthUser u WHERE (:userName IS NULL OR :userName = '' OR u.userName LIKE CONCAT('%', :userName, '%'))"
            + " AND (:email IS NULL OR :email = '' OR u.email = :email)"
            + " AND (u.userType = :type)", nativeQuery = false)
    Page<Object[]> listUserByType(@Param(value = "userName") String userName,
                                  @Param(value = "email") String email,
                                  @Param(value = "type") byte type,
                                  Pageable pageable);

    AuthUser findByEmail(String email);
    
    AuthUser findByUserNameAndPassword(String user,String pass);

    @Query(value = "SELECT au.id FROM AuthUser au "
            + " WHERE (au.userName = :user)"
            , nativeQuery = false)
    Long checkExistByUserName(@Param(value = "user") String user);
}
