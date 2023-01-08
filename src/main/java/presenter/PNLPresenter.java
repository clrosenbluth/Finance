package presenter;

import calculations.PNLTableValues;


public class PNLPresenter {
    private PNLTableValues tableValues;
    private String lastDate;
    private String firstDate;

    public PNLPresenter(PNLTableValues tableValues, String lastDate, String firstDate) {
        this.tableValues = tableValues;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    public void calculatePNLTable() {
        //TODO: implement
    }


    public double getOverallPnL() {
        double overallPNL = 0.0;

        double[] rowOfLastDate = tableValues.getPNLTable().get(lastDate);
        double[] rowOfFirstDate = tableValues.getPNLTable().get(firstDate);
        overallPNL =
                (rowOfLastDate[0] + rowOfLastDate[1]) - (rowOfFirstDate[0] + rowOfFirstDate[1]);

        return overallPNL;
    }
}
