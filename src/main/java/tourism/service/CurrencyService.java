package tourism.service;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import tourism.model.ResponseFromCurrencyAPI;
import java.io.IOException;
import java.net.URL;

@Service
public class CurrencyService {
    private final OkHttpClient client;

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
        ResponseFromCurrencyAPI resp = new Gson().fromJson(callApi().body().string(), ResponseFromCurrencyAPI.class);
        return resp;
    }

    public double getPriceInEuro(double priceInDkk) throws IOException {
        ResponseFromCurrencyAPI response = getResponseAsObj();
        double rateDkkEuro = response.getRates().getDKK() / response.getRates().getEUR();
        return Math.round(priceInDkk * rateDkkEuro * 100.0) / 100.0; //returns rounded to two decimals value
    }
}
