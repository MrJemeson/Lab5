package ru.bmstu.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.bmstu.services.LoggerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

@Service
public class LoggerServiceImpl implements LoggerService {
    private final Path logFile;

    @Autowired
    public LoggerServiceImpl(@Value("${log.file.path}") String logFilePath) {
        this.logFile = Paths.get(logFilePath);
    }

    @Override
    public void log(String message) {
        try {
            Files.createDirectories(logFile.getParent());
            String logLine = LocalDateTime.now() + " - " + message + System.lineSeparator();
            Files.writeString(logFile, logLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Failed to write log", e);
        }
    }
}
