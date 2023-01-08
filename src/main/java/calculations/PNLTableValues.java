package calculations;

import api_data.FXData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PNLTableValues {
    String[] currencies = {"ILS", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNH", "HKD"};
    private PositionAndPresentValueAtTime positionAndPresentValueAtTime;
    private HashMap<String, double[]> PNLTable;
    private ArrayList<String[]> records;

    public HashMap<String, double[]> getPNLTable() {
        return PNLTable;
    }

    public PNLTableValues(PositionAndPresentValueAtTime positionAndPresentValueAtTime) {
        this.PNLTable = new HashMap<>();
        this.positionAndPresentValueAtTime = positionAndPresentValueAtTime;
        this.records = positionAndPresentValueAtTime.records;
        setPnlTable();
    }

    private void setPnlTable() {
        String previousDate = null;
        for (String[] record : records) {
            HashMap<String, Double> positions = positionAndPresentValueAtTime.getPosition();
            String date = record[0];

            double[] pnlTableValues = new double[3];
            if (record == records.get(records.size() - 1)) {
                pnlTableValues[0] = getTotalFXPositionAtPresent(positions);
            } else {
                pnlTableValues[0] = getTotalFXPositionAtClose(positions, date);
            }
            pnlTableValues[1] = positions.get("USD");
            pnlTableValues[2] = getPNLValue(date, previousDate);

            PNLTable.put(record[0], pnlTableValues);

            previousDate = date;
        }
    }

    /**
     * @return current fx position held in USD at close
     */
    private double getTotalFXPositionAtClose(HashMap<String, Double> positions, String date) {
        double currentPosition = 0.0;
        //traverse through positions and calculate values(units of fx)/rate from that days close
        currentPosition += positions.get("USD");

        for (String curr : currencies) {
            double pos = positions.get(curr);
            FXData fxData = new FXData(curr);
            double rate = fxData.getClose(date);
            currentPosition += pos / rate;
        }

        return currentPosition;
    }

    /**
     * @return current fx position held in USD at present for todays pnl row
     */
    private double getTotalFXPositionAtPresent(HashMap<String, Double> positions) {
        double currentPosition = 0.0;
        //traverse through positions and calculate values(units of fx)/rate current if today
        currentPosition += positions.get("USD");

        for (String curr : currencies) {
            double pos = positions.get(curr);
            FXData fxData = new FXData(curr);
            double rate = fxData.getRealTimeFXRate();
            currentPosition += pos / rate;
        }

        return currentPosition;
    }

    /**
     * @return pnl between previous and current day
     */
    private double getPNLValue(String date, String previousDate) {
        double pnlValue = 0.0;
        if(previousDate != null){
            pnlValue = (PNLTable.get(date)[0] + PNLTable.get(date)[1]) - (PNLTable.get(previousDate)[0] + PNLTable.get(previousDate)[1]);
        }

        return pnlValue;
    }

}
