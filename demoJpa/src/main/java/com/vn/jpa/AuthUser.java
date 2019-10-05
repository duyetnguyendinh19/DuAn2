package com.vn.jpa;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "auth_user")
@NamedQuery(name = "AuthUser.findAll", query = "SELECT au FROM AuthUser au")
public class AuthUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private String gender;

    private String salt;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "is_verified")
    private byte isVerified;

    private byte status;

    @Column(name = "user_type")
    private byte userType;

    @ManyToMany
    @JoinTable(name = "auth_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> authRoles = new ArrayList();

    public AuthUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(byte isVerified) {
        this.isVerified = isVerified;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getStatus() {
        return this.status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getAuthRoles() {
        return this.authRoles;
    }

    public void setAuthRoles(List<Role> authRoles) {
        this.authRoles = authRoles;
    }

    public byte getUserType() {
        return this.userType;
    }

    public void setUserType(byte userType) {
        this.userType = userType;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = this.getAuthRoles();
        if (roles == null) {
            return Collections.emptyList();
        } else {
            List<GrantedAuthority> grantedAuths = new ArrayList();
            Iterator var3 = roles.iterator();

            while(var3.hasNext()) {
                Role role = (Role)var3.next();
                grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
            }

            return grantedAuths;
        }
    }

    public String getUsername() {
        return this.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
