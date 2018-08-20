package bddtester.core.testdata;

public class TwoDataType<T, R>
{
    private T first;

    private R second;

    public TwoDataType(final T first, final R second)
    {
        this.first = first;
        this.second = second;
    }

    public T getFirst()
    {
        return first;
    }

    public R getSecond()
    {
        return second;
    }

    @Override
    public String toString()
    {
        return "First: " + getFirst().toString() + "\nSecond: " + getSecond().toString();
    }

}
