package ru.bmstu;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstu.config.AppConfig;
import ru.bmstu.services.impl.UIServiceImpl;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws CsvValidationException, IOException {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UIServiceImpl ui = context.getBean(UIServiceImpl.class);
            ui.start();
        }
    }
}
