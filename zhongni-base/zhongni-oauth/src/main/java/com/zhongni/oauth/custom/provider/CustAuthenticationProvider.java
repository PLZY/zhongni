package com.zhongni.oauth.custom.provider;

import com.zhongni.oauth.custom.token.CustUsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class CustAuthenticationProvider extends DaoAuthenticationProvider {
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        UserDetails user = this.getUserDetailsService().loadUserByUsername(username);
        checkUser(user, authentication);
        return createSuccessAuthentication(user, authentication, user);
    }

    private void checkUser(UserDetails user, Authentication authentication)
    {
        if (user == null) {
            throw new InternalAuthenticationServiceException(
                    "用户不存在");
        }

        if (!user.isAccountNonLocked()) {
            throw new LockedException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.locked", "User account is locked"));
        }
        if (!user.isEnabled()) {
            throw new DisabledException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.disabled", "User is disabled"));
        }
        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired"));
        }
        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(this.messages
                    .getMessage("AccountStatusUserDetailsChecker.credentialsExpired", "User credentials have expired"));
        }

        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码为空"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, user.getPassword())) {
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码不正确"));
        }
    }

    /**
     * 指定当前的Provider只支持token类型是CustUsernamePasswordAuthenticationToken的，用以实现不同登录方式的区分
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (CustUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }

}
