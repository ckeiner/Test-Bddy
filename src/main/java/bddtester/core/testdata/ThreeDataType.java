package bddtester.core.testdata;

public class ThreeDataType<T, R, S>
{
    private T first;

    private R second;

    private S third;

    public ThreeDataType(final T first, final R second, final S third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst()
    {
        return first;
    }

    public R getSecond()
    {
        return second;
    }

    public S getThird()
    {
        return third;
    }
}
