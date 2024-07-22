package kz.tempest.tpapp.modules.person.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Right;
import kz.tempest.tpapp.commons.models.ExtensionInfo;
import kz.tempest.tpapp.commons.models.ModuleExtensionRight;
import kz.tempest.tpapp.commons.models.ModuleInfo;
import kz.tempest.tpapp.modules.person.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.Nested;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Table(name = "persons")
@Entity(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Lob
    @JsonIgnore
    @Column(name = "image", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] image;
    @Column(name = "active", columnDefinition = "TINYINT")
    private boolean active;

    @Embedded
    private PersonInformation information = new PersonInformation();

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "person_roles", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "role")
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "person_module_extension_rights", joinColumns = @JoinColumn(name = "person_id"))
    private List<ModuleExtensionRight> personModuleExtensionRights = new ArrayList<>();


    public Person (String email, String password, byte[] image, boolean active) {
        this.email = email;
        this.password = password;
        this.image = image;
        this.active = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
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
        return active;
    }

    public static Person getPerson(Authentication auth) {
        return (Person) auth.getPrincipal();
    }

    public boolean is(Role role) {
        return roles.contains(role);
    }

    public boolean isAdmin() {
        return is(Role.ADMIN);
    }

    public boolean isDeveloper() {
        return is(Role.DEVELOPER);
    }

    public boolean isAnalyst() {
        return is(Role.ANALYST);
    }

    public boolean isEmployee() {
        return is(Role.EMPLOYEE);
    }

    public boolean isUser() {
        return roles.contains(Role.USER);
    }


}
