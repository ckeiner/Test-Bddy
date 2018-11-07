package com.ckeiner.testbddy.core.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.ckeiner.testbddy.core.bdd.Feature;
import com.ckeiner.testbddy.core.bdd.scenario.AbstractScenario;

public class JUnitScenarioRunner extends ParentRunner<AbstractScenario>
{
    Feature feature;

    List<AbstractScenario> scenarios;

    Description parentDescription;

    public JUnitScenarioRunner(Class<?> testClass, Feature feature, Description parentDescription)
            throws InitializationError
    {
        super(testClass);
        this.feature = feature;
        scenarios = new ArrayList<>(feature.getScenarios().size());
        scenarios.addAll(feature.getScenarios());
        this.parentDescription = parentDescription;
    }

    @Override
    protected List<AbstractScenario> getChildren()
    {
        return scenarios;
    }

    @Override
    protected Description describeChild(AbstractScenario child)
    {
        Description description = Description.createSuiteDescription(child.getDescription());
        parentDescription.addChild(description);
        return description;
    }

    @Override
    protected void runChild(AbstractScenario child, RunNotifier notifier)
    {
        Description childDescription = describeChild(child);
        notifier.fireTestStarted(childDescription);
        child.test();
        notifier.fireTestFinished(childDescription);
    }

}
