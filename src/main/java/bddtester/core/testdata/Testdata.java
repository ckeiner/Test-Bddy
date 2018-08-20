package bddtester.core.testdata;

import bddtester.core.bdd.scenario.ScenarioOutline;

/**
 * This class holds the test data for a {@link ScenarioOutline}.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the test data
 */
public abstract class Testdata<T>
{
    T testdata;

    public T getData()
    {
        if (testdata == null)
        {
            testdata = defineData();
        }
        return testdata;
    };

    protected abstract T defineData();
}
