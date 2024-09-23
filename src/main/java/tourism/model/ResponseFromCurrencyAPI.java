package tourism.model;

public class ResponseFromCurrencyAPI {


    private Rate rates;

    public Rate getRates() {
        return rates;
    }

    public ResponseFromCurrencyAPI(Rate rates) {
        this.rates = rates;
    }

    public static class Rate {

        private double DKK;
        private double EUR;

        public double getDKK() {
            return DKK;
        }

        public double getEUR() {
            return EUR;
        }
    }

    @Override
    public String toString() {
        return "DKK: " + rates.DKK + " EUR: " + rates.EUR;
    }
}
