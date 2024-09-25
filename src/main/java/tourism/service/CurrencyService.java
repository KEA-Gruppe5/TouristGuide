package tourism.service;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import tourism.model.ResponseFromCurrencyAPI;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class CurrencyService {
    private final OkHttpClient client;
    private static final Logger logger = Logger.getLogger("CurrencyLogger");

    public CurrencyService(OkHttpClient client) {
        this.client = client;
    }

    public Response callApi() throws IOException {
        URL url = new URL("https://cdn.forexvalutaomregner.dk/api/latest.json");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    public ResponseFromCurrencyAPI getResponseAsObj() throws IOException {
        try(Response response = callApi()){
            if(response != null && response.body() != null){
                String json = Objects.requireNonNull(response.body().string());
                return new Gson().fromJson(json, ResponseFromCurrencyAPI.class);
            }
            return null;
        }

    }

    public double getPriceInEuro(double priceInDkk) throws IOException {
        logger.info("price in dkk: " + priceInDkk);
        if(priceInDkk == 0){
            return 0;
        }
        ResponseFromCurrencyAPI response = getResponseAsObj();
        double rateDkkEuro = response.getRates().getDKK() / response.getRates().getEUR(); // getting the 7.5 rate
        double convertedPrice = Math.round(priceInDkk / rateDkkEuro * 100.0) / 100.0; //returns rounded to two decimals value
        logger.info("converting to euro: " + convertedPrice);
        return convertedPrice;
    }
}
