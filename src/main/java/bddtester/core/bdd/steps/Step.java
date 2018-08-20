package bddtester.core.bdd.steps;

import com.aventstack.extentreports.GherkinKeyword;

/**
 * Describes a BDD Step without test data.
 * 
 * @author ckeiner
 */
public class Step extends AbstractStep<Runnable>
{

    public Step(GherkinKeyword keyword, String description, Runnable behavior)
    {
        super(keyword, description, behavior);
    }

    @Override
    protected void executeStep()
    {
        getBehavior().run();
    }

}
