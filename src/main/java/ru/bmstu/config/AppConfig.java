package ru.bmstu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import ru.bmstu.repositories.UserRepository;
import ru.bmstu.repositories.impl.CsvUserRepositoryImpl;

@Configuration
@ComponentScan("ru.bmstu")
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public CsvUserRepositoryImpl CsvUserRepository() {
        return new CsvUserRepositoryImpl(new FileSystemResource("src/main/resources/data/users.csv"));
    }
}
