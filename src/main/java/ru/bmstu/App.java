package ru.bmstu;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstu.components.UI;
import ru.bmstu.config.AppConfig;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws CsvValidationException, IOException {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UI ui = context.getBean(UI.class);
            ui.start();
        }
    }
}
