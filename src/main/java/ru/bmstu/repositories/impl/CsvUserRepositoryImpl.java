package ru.bmstu.repositories.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import ru.bmstu.objects.User;
import ru.bmstu.repositories.UserRepository;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CsvUserRepositoryImpl implements UserRepository {
    private final Resource csvResource;
    private final String baseDir;


    public CsvUserRepositoryImpl(@Value("${app.data.file}") Resource resource) {
        this.csvResource = resource;
        String jarDir;
        try {
            jarDir = Paths.get(UserRepository.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString();
        } catch (Exception e) {
            jarDir = System.getProperty("user.dir");
        }
        this.baseDir = jarDir;
    }

    @Override
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new java.io.FileInputStream(resolveFile(csvResource))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        User user = parseCsvLine(line);
                        users.add(user);
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV", e);
        }
        return users;
    }

    @Override
    public User parseCsvLine(String line) {
        String[] stringParts = line.split(",", -1);
        if (stringParts.length < 4) {
            throw new IllegalArgumentException("Invalid CSV format");
        }
        int id = Integer.parseInt(stringParts[0].trim());
        String fullName = stringParts[1].trim();
        String role = stringParts[2].trim();
        Integer tokens = !role.equals("Teacher") ? Integer.parseInt(stringParts[3].trim()) : null;
        return new User(id, fullName, role, tokens);
    }

    @Override
    public void saveUsers(List<User> users) {
        try {
            java.io.File file = resolveFile(csvResource);
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
                for (User user : users) {
                    writer.println(toCsvLine(user));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }


    @Override
    public File resolveFile(Resource resource) throws IOException {
        String filename = resource.getFilename();
        File file = new File(baseDir + "/data", filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @Override
    public String toCsvLine(User user) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(Integer.toString(user.getID()))
                .add(user.getFULL_NAME())
                .add(user.getROLE())
                .add(((user.getTokens() == null)?(""):(Integer.toString(user.getTokens()))));

        return joiner.toString();
    }
}
