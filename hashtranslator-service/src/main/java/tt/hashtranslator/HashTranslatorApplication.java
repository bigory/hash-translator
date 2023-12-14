package tt.hashtranslator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tt.hashtranslator.config.HashTranslatorProperties;

@SpringBootApplication
@EnableConfigurationProperties(HashTranslatorProperties.class)
public class HashTranslatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HashTranslatorApplication.class, args);
    }

}
