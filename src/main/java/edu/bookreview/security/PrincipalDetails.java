package edu.bookreview.security;

import edu.bookreview.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user;

    // 권한이 한 개가 아닐 수 있기 때문에 Collection 을 반환해야 한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override // 계정이 만료되지 않은지를 반환한다.
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정이 잠겨있지 않은지를 반환한다.
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 계정의 패스워드가 만료되지 않은지를 반환한다.
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override  // 사용가능한 계정인지를 반환한다.
    public boolean isEnabled() {
        return true;
    }
}
