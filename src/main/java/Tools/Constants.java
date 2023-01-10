package Tools;

public enum Constants
{
    SPOT ("Spot"),
    FUTURE ("Future");

    public final String label;

    private Constants(String label)
    {
        this.label = label;
    }
}
