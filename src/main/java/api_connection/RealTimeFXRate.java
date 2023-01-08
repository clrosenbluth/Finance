package api_connection;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

public class RealTimeFXRate {
    @SerializedName("Realtime Currency Exchange Rate")
    public LinkedTreeMap<String, String> realTimeExchangeRate;

    public RealTimeFXRate(LinkedTreeMap<String, String> realTimeExchangeRate){
        this.realTimeExchangeRate = realTimeExchangeRate;
    }
}
