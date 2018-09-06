package bddtester.core.bdd.beforeAfter;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps multiple {@link After}s in a List.
 * 
 * @author ckeiner
 *
 */
public class Afters
{
    /**
     * Contains all {@link After}s.
     */
    private final List<After> afters;

    /**
     * Assigns an empty <code>ArrayList</code> to {@link #afters}.
     */
    public Afters()
    {
        this(new ArrayList<>());
    }

    /**
     * Assigns the parameter to {@link #afters}.
     * 
     * @param afters
     *            The list of {@link After}s to add to the list.
     */
    public Afters(List<After> afters)
    {
        this.afters = afters;
    }

    /**
     * Adds an {@link After} to {@link #afters}.
     * 
     * @param after
     *            The <code>After</code> to add to the list.
     */
    public void addAfter(After after)
    {
        getAfters().add(after);
    }

    public List<After> getAfters()
    {
        return afters;
    }

}
