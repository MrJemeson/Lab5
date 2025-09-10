package ru.bmstu.repositories;


import com.opencsv.exceptions.CsvValidationException;
import ru.bmstu.objects.User;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    List<User> loadUsers() throws IOException, CsvValidationException;
    User parseCsvLine(String[] parts);
    void saveUsers(List<User> users);
    String toCsvLine(User user);
}
