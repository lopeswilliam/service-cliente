package br.com.cadastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.cadastro")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
