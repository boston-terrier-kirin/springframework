package com.springboot.blog.security;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user != null){
            List<SimpleGrantedAuthority> roleList = user.getRoles()
                                                        .stream()
                                                        .map((role) -> new SimpleGrantedAuthority(role.getName()))
                                                        .collect(Collectors.toList());

            org.springframework.security.core.userdetails.User authenticatedUser =
                    new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            roleList
                    );

            return authenticatedUser;

        }

        throw new UsernameNotFoundException("Invalid username and password");
    }
}