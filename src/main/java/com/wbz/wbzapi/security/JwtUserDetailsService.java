package com.wbz.wbzapi.security;

import com.wbz.wbzapi.entity.User;
import com.wbz.wbzapi.security.jwt.JwtUser;
import com.wbz.wbzapi.security.jwt.JwtUserFactory;
import com.wbz.wbzapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger LOG = Logger.getLogger(JwtUserDetailsService.class.getName());

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        LOG.info("IN loadUserByUsername - user with username: " + username + " successfully loaded");
        return jwtUser;
    }
}
