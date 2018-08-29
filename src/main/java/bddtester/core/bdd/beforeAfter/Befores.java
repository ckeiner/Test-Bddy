package bddtester.core.bdd.beforeAfter;

import java.util.ArrayList;
import java.util.List;

public class Befores
{
    private final List<Before> befores;

    public Befores()
    {
        this(new ArrayList<>());
    }

    public Befores(List<Before> befores)
    {
        this.befores = befores;
    }

    public void addBefore(Before before)
    {
        getBefores().add(before);
    }

    public List<Before> getBefores()
    {
        return befores;
    }
}
