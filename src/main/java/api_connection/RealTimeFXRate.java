package api_connection;

import com.google.gson.internal.LinkedTreeMap;

public class RealTimeFXRate {
    public LinkedTreeMap<String, Double> rates;

    public RealTimeFXRate(LinkedTreeMap<String, Double> rates){
        this.rates = rates;
    }
}
