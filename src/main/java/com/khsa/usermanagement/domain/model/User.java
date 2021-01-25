package com.khsa.usermanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.khsa.usermanagement.repository.validation.EntityExist;
import com.khsa.usermanagement.util.DeserializerRoleJsonConverter;
import com.khsa.usermanagement.util.GenderConverter;
import com.khsa.usermanagement.util.PasswordConverter;
import com.khsa.usermanagement.util.SerializerRoleJsonConverter;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    //Static parameters
    public static final String ROLE_VALIDATION_MESSAGE = "There is no such role(s)";
    public static final String PASSWORD_VALIDATION_MESSAGE = "Password must contain one uppercase letter and one digit";
    private static final long serialVersionUID = 4228579613375135370L;

    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

//    @Transient
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "id")
//    private Customer customerId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "login", unique = true)
    private String username;

    @NotNull
    @Pattern(regexp = "(\\S*\\p{Upper}+\\S*\\d+\\S*)|(\\S*\\d+\\S*\\p{Upper}+\\S*)",
            message = PASSWORD_VALIDATION_MESSAGE)
    @Convert(converter = PasswordConverter.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender")
    private Gender gender;

    @EntityExist(message = ROLE_VALIDATION_MESSAGE)
    @JsonDeserialize(contentConverter = DeserializerRoleJsonConverter.class)
    @JsonSerialize(contentConverter = SerializerRoleJsonConverter.class)
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
}
