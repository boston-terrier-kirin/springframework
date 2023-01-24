package com.example.service;

import com.example.model.SignupRequest;
import com.example.repository.CustomerRepository;
import com.example.entity.Customer;
import com.example.model.SecurityCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ★重要な気づき★
 * UserDetailsServiceを公開すればSpringが勝手に見つけてくれる。
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(SignupRequest signupRequest) {
        Customer customer = new Customer();
        customer.setEmail(signupRequest.getEmail());
        customer.setPwd(this.passwordEncoder.encode(signupRequest.getPassword()));
        customer.setRole("user");

        this.customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = this.customerRepository.findByEmail(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User details not found for: " + username);
        }

        return new SecurityCustomer(customer);
    }
}
