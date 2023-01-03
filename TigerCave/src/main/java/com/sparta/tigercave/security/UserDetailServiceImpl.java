package com.sparta.tigercave.security;

import com.sparta.tigercave.entity.Users;
<<<<<<< HEAD
import com.sparta.tigercave.exception.CustomException;
=======
>>>>>>> origin/develop
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import static com.sparta.tigercave.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
=======
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

>>>>>>> origin/develop
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
<<<<<<< HEAD
        Users users = usersRepository.findByUsername(username).orElseThrow(() ->
                new CustomException(USER_NOT_FOUND));
        return new UserDetailImpl(users, users.getUsername(), users.getPassword());
    }
=======
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new UserDetailImpl(users, users.getUsername());
    }

>>>>>>> origin/develop
}
