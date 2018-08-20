package bddtester.core.bdd.steps;

import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.testdata.Testdata;

/**
 * Describes a BDD Step with {@link Testdata}.
 *
 * @param <T>
 *            The type of the test data.
 * @author ckeiner
 */
public class TypeStep<T> extends AbstractStep<Consumer<T>>
{
    protected T testdata;

    public TypeStep(final GherkinKeyword keyword, final String description, final Consumer<T> behavior)
    {
        this(keyword, description, behavior, null);
    }

    public TypeStep(final GherkinKeyword keyword, final String description, final Consumer<T> behavior,
            final T testdata)
    {
        super(keyword, description, behavior);
        this.testdata = testdata;
    }

    /**
     * Defines the test data for the step.
     * 
     * @param testdata
     *            The test data of the step.
     * @return The TypeStep so it's chainable.
     */
    public TypeStep<T> withData(T testdata)
    {
        this.testdata = testdata;
        return this;
    }

    /**
     * Tests the step with the supplied test data.
     * 
     * @param testdata
     *            The test data of the step.
     */
    public void test(T testdata)
    {
        this.testdata = testdata;
        test();
    }

    // @Override
    // public void skip()
    // {
    // ReportElement element = setUpReporter();
    // // Mark the node as skipped
    // if (element != null)
    // {
    // element.skip(getDescription() + " with Data " + getTestdata());
    // }
    // }

    @Override
    protected void executeStep()
    {
        // Throw an error if the testdata is null
        if (testdata == null)
        {
            throw new IllegalStateException("Testdata cannot be null, please supply testdata");
        }
        // Execute the consumer
        getBehavior().accept(testdata);
    }

    public T getTestdata()
    {
        return testdata;
    }

}
