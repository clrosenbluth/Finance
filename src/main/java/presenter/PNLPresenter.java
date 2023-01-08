package presenter;

import calculations.PNLTableValues;

public class PNLPresenter {
    private PNLTableValues tableValues;

    public PNLPresenter (PNLTableValues tableValues) {
        this.tableValues = tableValues;
    }

    /* public double getOverallPnL(){
        ((lastDate)pnlTableValues[0]+pnlTableValues[1]) - (firstDate)(pnlTableValues[0]+pnlTableValues[1])
    }*/
}
