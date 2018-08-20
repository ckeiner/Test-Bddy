package bddtester.core.bdd.background;

import java.util.ArrayList;
import java.util.List;

public class Backgrounds
{
    private final List<Background> backgrounds;

    public Backgrounds()
    {
        this(new ArrayList<>());
    }

    public Backgrounds(List<Background> backgrounds)
    {
        this.backgrounds = backgrounds;
    }

    public void addBackground(Background background)
    {
        getBackgrounds().add(background);
    }

    public List<Background> getBackgrounds()
    {
        return backgrounds;
    }
}
