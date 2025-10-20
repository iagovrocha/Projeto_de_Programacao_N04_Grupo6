package com.projeto.semana1_projetoprog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Semana1ProjetoprogApplication {

    public static void main(String[] args) {
        SpringApplication.run(Semana1ProjetoprogApplication.class, args);
    }

    public static Semana1ProjetoprogApplication createSemana1ProjetoprogApplication() {
        return new Semana1ProjetoprogApplication();
    }
}
