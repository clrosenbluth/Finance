public class RateCalculator
{
    public RateCalculator()
    {}

    public Float getLinearRate (Float spot, Float futureRate, Integer days)
    {
        return (365 / days) * ((futureRate / spot) - 1);
    }

    public Float getContRate (Float futureRate, Integer days)
    {
        return (float) (365 * Math.log(futureRate)) / days;
    }
}
