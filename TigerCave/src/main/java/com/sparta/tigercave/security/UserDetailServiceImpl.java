package com.sparta.tigercave.security;

import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static com.sparta.tigercave.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new CustomException(USER_NOT_FOUND));
        return new UserDetailImpl(user, user.getId(),user.getUsername(), user.getPassword());
    }
}