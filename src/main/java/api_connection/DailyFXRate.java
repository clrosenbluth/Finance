package api_connection;


import com.google.gson.internal.LinkedTreeMap;

public class DailyFXRate {
    public LinkedTreeMap<String, LinkedTreeMap<String, Double>> rates;

    public DailyFXRate(LinkedTreeMap<String, LinkedTreeMap<String, Double>> rates) {
        this.rates = rates;
    }

}
