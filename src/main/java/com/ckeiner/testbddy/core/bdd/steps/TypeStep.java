package com.ckeiner.testbddy.core.bdd.steps;

import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;
import com.ckeiner.testbddy.core.reporting.ReportElement;
import com.ckeiner.testbddy.core.util.ParameterResolver;

/**
 * Describes a BDD Step with some test data.
 *
 * @param <T>
 *            The type of the test data.
 * @author ckeiner
 */
public class TypeStep<T> extends AbstractStep<Consumer<T>>
{
    /**
     * The testdata for this step.
     */
    protected T testdata;

    /**
     * Creates a TypeStep with the specified keyword, description, behavior and
     * without test data.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} describing whether its a given, when,
     *            then or and step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            A consumer containing the behavior.
     * @see AbstractStep#AbstractStep(GherkinKeyword, String, Object)
     */
    public TypeStep(final GherkinKeyword keyword, final String description, final Consumer<T> behavior)
    {
        this(keyword, description, behavior, null);
    }

    /**
     * Creates a TypeStep with the specified keyword, description, behavior and test
     * data.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} describing whether its a given, when,
     *            then or and step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            A consumer containing the behavior.
     * @param testdata
     *            The test data for this step.
     * @see AbstractStep#AbstractStep(GherkinKeyword, String, Object)
     */
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
     * @return This TypeStep.
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

    /**
     * Sets the report element up with the resolved description.<br>
     * In other words, the report element receives a description, in which all
     * placeholders have been resolved.
     */
    @Override
    protected ReportElement setUpReporter()
    {
        ParameterResolver<T> resolver = new ParameterResolver<>();
        setDescription(resolver.resolvePlaceholders(getDescription(), testdata));
        return super.setUpReporter(true, getDescription());
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

    /**
     * Executes the consumer with {@link #testdata} as parameter.
     * 
     * @throws IllegalStateException
     *             If {@link #testdata} is <code>null</code>.
     */
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
