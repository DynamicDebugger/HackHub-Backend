package com.bs.service;

import com.bs.config.JwtProvider;
import com.bs.model.User;
import com.bs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null) throw new Exception("user not found");
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new Exception("user not found");
        return optionalUser.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        int curSize = user.getProjectSize();
        user.setProjectSize(curSize + number);
        return userRepository.save(user);
    }
}
