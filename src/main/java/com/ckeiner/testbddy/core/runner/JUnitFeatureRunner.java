package com.ckeiner.testbddy.core.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.ckeiner.testbddy.core.bdd.Feature;

/**
 * The first try at a custom runner, which executes a single test method
 * 
 * @author ckeiner
 *
 */
public class JUnitFeatureRunner extends ParentRunner<Feature>
{
    private final List<Feature> features;

    public JUnitFeatureRunner(final Class<?> testClass) throws InitializationError
    {
        super(testClass);
        features = createRunners(testClass);
    }

    private List<Feature> createRunners(final Class<?> testClass)
    {
        List<Feature> features = new ArrayList<Feature>();
        try
        {
            testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e)
        {
            System.out.println("Could not create specification because" + e.getMessage());
            throw new IllegalArgumentException(e);
        }
        Feature feature;
        try
        {
            feature = (Feature) testClass.getField("feature").get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e)
        {
            System.out.println("Could not find field feature" + e.getMessage());
            throw new IllegalArgumentException(e);
        }
        features.add(feature);
        return features;
    }

    @Override
    protected List<Feature> getChildren()
    {
        return features;
    }

    @Override
    protected Description describeChild(final Feature child)
    {
        Description description = Description.createSuiteDescription(child.getDescription());
        return description;
    }

    @Override
    protected void runChild(final Feature child, final RunNotifier notifier)
    {
        JUnitScenarioRunner runner = createJunitScenarioRunner(child, describeChild(child));
        runner.run(notifier);
    }

    private JUnitScenarioRunner createJunitScenarioRunner(Feature child, Description description)
    {
        JUnitScenarioRunner runner = null;
        try
        {
            runner = new JUnitScenarioRunner(this.getClass(), child, description);
        } catch (InitializationError e)
        {
            e.printStackTrace();
        }
        return runner;
    }
}
