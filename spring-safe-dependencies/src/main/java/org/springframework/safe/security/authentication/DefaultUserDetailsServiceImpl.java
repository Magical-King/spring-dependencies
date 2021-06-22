package org.springframework.safe.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Sir.D
 */
@Slf4j
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("=====> Please configure the UserDetailsService of the interface ！");
        throw new UsernameNotFoundException(username);
    }

}
