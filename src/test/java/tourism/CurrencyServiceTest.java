package tourism;

import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourism.service.CurrencyService;

import java.io.IOException;

@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    CurrencyService currencyService;

    @Test
    public void getRates() throws IOException {
        Response response = currencyService.callApi();
        System.out.println(response.body().string());
    }

    @Test
    public void mapResp() throws IOException {
        System.out.println(currencyService.getResponseAsObj());
    }
}
