package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listar() {
        return userRepository.findAll();
    }

    public User cadastrar(User user) {
        return userRepository.save(user);
    }

    public User mudarSenha(User user) {

        return userRepository.save(user);
    }

    public User desabilitarConta(User user) {
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @PostConstruct
    public User createUser() {
    	User user = new User();
    	user.setLogin("teste@gmail.com");
    	user.setPassword(new BCryptPasswordEncoder().encode("teste"));
    	user.setEnabled(true);
    	return userRepository.save(user);
    }

}
