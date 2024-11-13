package org.dismefront.data.user;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Set;

import lombok.Data;
import org.dismefront.data.shared.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "IS1_USER")
@Data
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String password;

  @ElementCollection(targetClass = Role.class)
  @CollectionTable(name = "IS1_USER_ROLES", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Set<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
