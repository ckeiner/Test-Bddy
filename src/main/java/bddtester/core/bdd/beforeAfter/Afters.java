package bddtester.core.bdd.beforeAfter;

import java.util.ArrayList;
import java.util.List;

public class Afters
{
    private final List<After> afters;

    public Afters()
    {
        this(new ArrayList<>());
    }

    public Afters(List<After> afters)
    {
        this.afters = afters;
    }

    public void addAfter(After after)
    {
        getAfters().add(after);
    }

    public List<After> getAfters()
    {
        return afters;
    }

}
