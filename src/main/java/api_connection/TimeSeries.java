package api_connection;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;


public class TimeSeries {
    @SerializedName("Time Series FX (Daily)")
    public
    LinkedTreeMap<String, LinkedTreeMap> timeSeriesFX;

    public TimeSeries(LinkedTreeMap<String, LinkedTreeMap> timeSeriesFX){
        this.timeSeriesFX = timeSeriesFX;
    }

}
