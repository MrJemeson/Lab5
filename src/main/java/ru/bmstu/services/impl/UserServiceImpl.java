package ru.bmstu.services.impl;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.objects.User;
import ru.bmstu.repositories.UserRepository;
import ru.bmstu.services.UserService;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(int id, int tokenDif) throws CsvValidationException, IOException {
        List<User> users = userRepository.loadUsers();
        User user = users.stream().filter(x -> x.getID() == id).toList().getFirst();
        user.setTokens(user.getTokens() + tokenDif);
        userRepository.saveUsers(users);
        return user;
    }

    @Override
    public User addUser(String fullName, String role) throws CsvValidationException, IOException {
        List<User> users = userRepository.loadUsers();
        User newUser = new User(users.stream().mapToInt(User::getID).max().orElse(0)+1, fullName, role, ((role.equals("Student"))?(10):(null)));
        users.add(newUser);
        userRepository.saveUsers(users);
        return newUser;
    }

    @Override
    public User deleteUser(int id) throws CsvValidationException, IOException {
        List<User> users = userRepository.loadUsers();
        User user = users.stream().filter(x -> x.getID() == id).toList().getFirst();
        users.remove(user);
        userRepository.saveUsers(users);
        return user;
    }

    @Override
    public List<User> getUsers() throws CsvValidationException, IOException {
        return userRepository.loadUsers();
    }
}
