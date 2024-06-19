package com.bonappetit.service;

import com.bonappetit.config.UserSession;
import com.bonappetit.model.dto.RecipeInfoDTO;
import com.bonappetit.model.dto.UserLoginDTO;
import com.bonappetit.model.dto.UserRegisterDTO;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean register(UserRegisterDTO registerDTO) {
        Optional<User> existingUser = this.userRepository.findByUsernameOrEmail(registerDTO.getUsername(), registerDTO.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();

        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> byUsername = this.userRepository.findByUsername(loginDTO.getUsername());

        if (byUsername.isEmpty()) {
            return false;
        }

        boolean passMatch = passwordEncoder.matches(loginDTO.getPassword(), byUsername.get().getPassword());

        if (!passMatch) {
            return false;
        }

        userSession.login(byUsername.get().getId(), byUsername.get().getUsername());

        return true;
    }

    @Transactional
    public List<Recipe> findFavourites(Long id) {
//        return userRepository.findById(id)
//                .map(User::getFavouriteRecipes)
//                .orElseGet(ArrayList::new);

        Optional<User> byId = userRepository.findById(id);

        if (byId.isEmpty()) {
            return new ArrayList<>();
        }

        return byId.get().getFavouriteRecipes();

    }
}
