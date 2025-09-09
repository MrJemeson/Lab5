package ru.bmstu.repositories;


import org.springframework.core.io.Resource;
import ru.bmstu.objects.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UserRepository {
    List<User> loadUsers();
    User parseCsvLine(String line);
    void saveUsers(List<User> users);
    File resolveFile(Resource resource) throws IOException;
    String toCsvLine(User user);
}
