package com.wbz.wbzapi.service.Impl;

import com.wbz.wbzapi.entity.Role;
import com.wbz.wbzapi.entity.Status;
import com.wbz.wbzapi.entity.User;
import com.wbz.wbzapi.handler.exception.UserNotFoundException;
import com.wbz.wbzapi.repository.RoleRepository;
import com.wbz.wbzapi.repository.UserRepository;
import com.wbz.wbzapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        User userToRegister = new User();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        userToRegister.setCreated(new Date());
        userToRegister.setUpdated(new Date());
        userToRegister.setUsername(user.getUsername());
        userToRegister.setEmail(user.getEmail());
        userToRegister.setPassword(passwordEncoder.encode(user.getPassword()));
        userToRegister.setRoles(userRoles);
        userToRegister.setStatus(Status.ACTIVE);

        userRepository.save(userToRegister);
        LOG.info("IN register - user: " + userToRegister + " succesfully registered");
        return userToRegister;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        LOG.info("IN getAll - " + result.size() + " users found");
        return result;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("Пользователь с USERNAME - %s отсутствует", username))
                );


    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("Пользователь с ID %d отсутствует", id))
                );
    }


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        LOG.info("IN delete - user with id: " + id + " deleted");
    }
}
