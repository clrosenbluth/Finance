public class RateCalculator
{
    public RateCalculator()
    {}

    public Float getContinuousRate (Float futureRate, Integer days)
    {
        return (float) (365 * Math.log(futureRate)) / days;
    }
}
