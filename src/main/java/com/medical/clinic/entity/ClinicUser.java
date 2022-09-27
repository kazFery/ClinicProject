package com.medical.clinic.entity;

import com.medical.clinic.constraint.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "users")
@Data
@NoArgsConstructor
public class ClinicUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private enum Sex {
        Female,
        Male
    }

    @Column(nullable = false, unique = true, length = 20)
    @NotNull(groups = Registration.class, message = "UserName is required")
    @Length(groups = Registration.class, min=4, max=20, message="UserName must be at least 4 characters long and no longer thn 20 characters")
    @Pattern(groups = Registration.class, regexp="^[a-z0-9]+$", message="UserName must contain only alphabetic characters and numbers")
    @UniqueUserName(groups = Registration.class, message = "Username already exist.")
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @NotNull(message = "Password is required")
    @ValidPassword(groups = Registration.class)
    private String password;

    @Transient
    private String passwordRepeat;

    @Pattern(groups = Others.class, regexp = "^[a-zA-Z]{4,30}$",message="Firstname  can only contains letters and must be between 4-30 characters long.")
    private String firstname;

    @Pattern(groups = Others.class, regexp = "^[a-zA-Z]{4,30}$",message="Lastname  can only contains letters and must be between 4-30 characters long.")
    private String lastname;

    @Past(groups = Others.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date DOB;

    @Pattern(groups = Others.class, regexp="^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$" , message="Phone number is not valid")
    private String phoneNo;

    @Column(nullable = false, unique = true,length = 150)
    @NotNull(groups = Registration.class, message = "Email is required")
    @Length(groups = Registration.class, max=150, message="Email must be less than 150 characters")
    @Email (groups = Registration.class, message="Email must have valid email format")
    @UniqueEmail(groups = Registration.class)
    private String email;

    @Enumerated(EnumType.STRING)
    private Sex gender;

    @Enumerated(EnumType.STRING)
    private Specialization  special;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return  true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(String roleName){
        return roleName.equals(role.name());
    }


}
