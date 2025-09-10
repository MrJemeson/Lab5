package ru.bmstu.services;


import com.opencsv.exceptions.CsvValidationException;
import ru.bmstu.objects.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User updateUser(int id, int token) throws CsvValidationException, IOException;
    User addUser(String fullName, String role) throws CsvValidationException, IOException;
    User deleteUser(int id) throws CsvValidationException, IOException;
    List<User> getUsers() throws CsvValidationException, IOException;
}
