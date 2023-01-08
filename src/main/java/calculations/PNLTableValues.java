package calculations;

import java.time.LocalDate;
import java.util.HashMap;

public class PNLTableValues {
    //private PositionAtTime positionAtTime
    private LocalDate firstDate;

    private LocalDate lastDate;
    private HashMap<LocalDate, double[]> PNLTable;

    public PNLTableValues()//add in PositionAtTime as parameter
    {
        //this.positionAtTime = positionAtTime
        //this.firstDate = 1st transaction date from sp in the PositionAtTime
        //this.lastDate = most recent
        PNLTable = new HashMap<>();
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    //for each date from first to last date
    //HashMap<String, Double> positions = store positionAtTime.getPositionAtTime in variable

    //pnlTableValues = new double[3]
    //pnlTableValues[0] = traverse through positions and calculate values(units of fx)*rate from that days close or current if today(see lines 63-69 in PositionAtTime)
    //pnlTableValues[1] = value of "USD" from positions;
    //pnlTableValues[2] = (pnlTableValues[0]+pnlTableValues[1]) - (previous)(pnlTableValues[0]+pnlTableValues[1]) OR 0 if first transaction

    //PNLTable.put(date, pnlTableValues);



}
