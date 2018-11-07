package com.ckeiner.testbddy.core.bdd.beforeAfter;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps multiple {@link Before}s in a List.
 * 
 * @author ckeiner
 *
 */
public class Befores
{
    /**
     * Contains all {@link Before}s.
     */
    private final List<Before> befores;

    /**
     * Assigns an empty <code>ArrayList</code> to {@link #befores}.
     */
    public Befores()
    {
        this(new ArrayList<>());
    }

    /**
     * Assigns the parameter to {@link #befores}.
     * 
     * @param befores
     *            The list of {@link Before}s to add to the list.
     */
    public Befores(List<Before> befores)
    {
        this.befores = befores;
    }

    /**
     * Adds an {@link Before} to {@link #befores}.
     * 
     * @param before
     *            The <code>Before</code> to add to the list.
     */
    public void addBefore(Before before)
    {
        getBefores().add(before);
    }

    public List<Before> getBefores()
    {
        return befores;
    }
}
