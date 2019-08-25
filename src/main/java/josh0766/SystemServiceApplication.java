package josh0766;

import josh0766.utility.ExternalCommandExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class SystemServiceApplication {

    @Bean
    public ExecutorService fixedThreadExecutor () {
        return Executors.newFixedThreadPool(32);
    }

    @Bean
    public ExternalCommandExecutor externalCommandExecutor () {
        return new ExternalCommandExecutor();
    }

    public static void main (String[] args) {
        SpringApplication.run(SystemServiceApplication.class, args);
    }
}
