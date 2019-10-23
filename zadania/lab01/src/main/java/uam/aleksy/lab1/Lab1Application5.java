package uam.aleksy.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uam.aleksy.lab1.yinyang.Yang;
import uam.aleksy.lab1.yinyang.Ying;

import javax.annotation.Resource;

@SpringBootApplication
public class Lab1Application5 {
    @Resource
    private Yang yang;

    @Resource
    private Ying ying;

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application5.class, args);
    }

    @Bean
    public Ying ying(Yang yang) {
        System.out.println(yang);
        return new Ying(yang);
    }

    @Bean
    public Yang yang() {
        System.out.println(ying);
        return new Yang(ying);
    }
}
