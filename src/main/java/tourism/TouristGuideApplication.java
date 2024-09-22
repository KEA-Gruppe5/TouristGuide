package tourism;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TouristGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristGuideApplication.class, args);
    }

    @Bean
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }
}
