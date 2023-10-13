package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security_service.SecurityUserDetailsService;
import ru.skypro.homework.security_service.MyUserDetails;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.interfaces.UserService;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
//    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final UserService userService;
    private final MyUserDetails myUserDetails;
    private final UserRepository userRepository;


//    @Override
//    public boolean login(String userName, String password) {
//        if (!manager.userExists(userName)) {
//            return false;
//        }
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        return encoder.matches(password, userDetails.getPassword());
//    }
    @Override
    public boolean login(String userName, String password) {
        User user = userRepository.findByUsername(userName);
       if(user == null) {
           log.info("Неправильные имя пользователя или пароль!");
           return false;
       }return encoder.matches(password, user.getPassword());
    }

//    @Override
//    public boolean register(Register register) {
//        if (manager.userExists(register.getUsername())) {
//            return false;
//        }
//        manager.createUser(
//                User.builder()
//                        .passwordEncoder(this.encoder::encode)
//                        .password(register.getPassword())
//                        .username(register.getUsername())
//                        .roles(register.getRole().name())
//                        .build());
//        return true;
//    }
    @Override
    public boolean register(Register register) {
        if (userRepository.findByUsername(register.getUsername())!= null) {
            return false;
        }
        userService.saveRegisterUser(register);
        return true;
    }


}
