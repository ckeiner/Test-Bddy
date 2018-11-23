package com.ckeiner.testbddy.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.ckeiner.testbddy.core.bdd.Feature;
import com.ckeiner.testbddy.core.bdd.scenario.AbstractScenario;
import com.ckeiner.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.ckeiner.testbddy.core.bdd.scenario.Scenario;
import com.ckeiner.testbddy.core.bdd.scenario.ScenarioOutline;
import com.ckeiner.testbddy.core.bdd.steps.Step;
import com.ckeiner.testbddy.core.bdd.steps.Steps;

public class BddSuite
{

    /**
     * Creates a {@link Feature} with the supplied description and the scenarios
     * specified in the {@link Supplier}.<br>
     * A single such supplier describes an {@link AbstractScenario} which is a
     * {@link Scenario} or {@link ScenarioOutline}. Each other Supplier adds another
     * Scenario or ScenarioOutline to the Feature.
     * 
     * @param description
     *            The description of the Feature.
     * @param scenarioSuppliers
     *            The Suppliers that contain an AbstractScenario.
     * @return Feature, that is described by the description and has the
     *         {@link AbstractScenario}s of the supplier.
     */
    @SafeVarargs
    public static Feature feature(final String description, final Supplier<AbstractScenario>... scenarioSuppliers)
    {
        // Convert Array of AbstractScenario Suppliers to a list of AbstractScenarios
        List<AbstractScenario> scenarios = new ArrayList<>();
        for (Supplier<AbstractScenario> scenarioSupplier : scenarioSuppliers)
        {
            scenarios.add(scenarioSupplier.get());
        }
        String currentClass = Thread.currentThread().getStackTrace()[2].getClassName();
        // If this is the current class, then we have the wrong one, so simply take the
        // next one
        if (currentClass.equals(BddSuite.class.getName()))
        {
            currentClass = Thread.currentThread().getStackTrace()[3].getClassName();
        }
        // Create the feature and return it
        return new Feature(currentClass + ": " + description, scenarios);
    }

    /**
     * Creates a {@link ScenarioOutline} with the specified description.<br>
     * The behavior and test data of the ScenarioOutline is inside the
     * {@link OutlineDescriptor}.
     * 
     * @param description
     *            The description of the scenario.
     * @param descriptor
     *            The OutlineDescriptor with the behavior and test data of the
     *            scenario.
     * @return A ScenarioOutline with the test data and behavior specified in the
     *         descriptor.
     */
    public static <T> ScenarioOutline<T> scenario(String description, OutlineDescriptor<T> descriptor)
    {
        ScenarioOutline<T> outline;
        if (descriptor != null)
        {
            outline = new ScenarioOutline<T>(description, descriptor.getSteps(), descriptor.getTestdata());
        }
        else
        {
            outline = new ScenarioOutline<T>(description, null, null);
        }
        return outline;
    }

    /**
     * Creates a {@link Scenario} with the specified description and steps.
     * 
     * @param description
     *            The description of the Scenario.
     * @param steps
     *            The {@link Steps} of the scenario.
     * @return A Scenario with the specified description and steps.
     */
    public static Scenario scenario(String description, Steps steps)
    {
        return new Scenario(description, steps);
    }

    /**
     * Creates a {@link OutlineDescriptor} with the specified test data.
     * 
     * @param testdata
     *            The test data for the OutlineDescriptor.
     * @return A OutlineDescriptor with the specified test data.
     */
    @SafeVarargs
    public static <T> OutlineDescriptor<T> withData(T... testdata)
    {
        return new OutlineDescriptor<T>(testdata);
    }

    /**
     * Creates a {@link OutlineDescriptor} without any test data.
     * 
     * @return A OutlineDescriptor without test data.
     */
    public static <T> OutlineDescriptor<T> withDataOfType(Class<T> clazz)
    {
        return new OutlineDescriptor<T>();
    }

    /*
     * GWAT
     */

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} who's {@link Step}s should be added.
     * 
     * @return The {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps and(Steps steps)
    {
        return new Steps().and(steps);
    }

    /**
     * Describes a single {@link Step} and returns it as {@link Steps}.
     * 
     * @param description
     *            The description of the Step.
     * @param runner
     *            The behavior of the Step.
     * @return An instance of {@link Steps} with the specified {@link Step} inside.
     */
    public static Steps and(String description, Runnable runner)
    {
        return new Steps().and(description, runner);
    }

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} who's {@link Step}s should be added.
     * 
     * @return The {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps given(Steps steps)
    {
        return new Steps().given(steps);
    }

    /**
     * Describes a single {@link Step} and returns it as {@link Steps}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return An instance of {@link Steps} with the specified {@link Step} inside.
     */
    public static Steps given(String description, Runnable runner)
    {
        return new Steps().given(description, runner);
    }

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} who's {@link Step}s should be added.
     * 
     * @return The {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps then(Steps steps)
    {
        return new Steps().then(steps);
    }

    /**
     * Describes a single {@link Step} and returns it as {@link Steps}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return An instance of {@link Steps} with the specified {@link Step} inside.
     */
    public static Steps then(String description, Runnable runner)
    {
        return new Steps().then(description, runner);
    }

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} who's {@link Step}s should be added.
     * 
     * @return The {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps when(Steps steps)
    {
        return new Steps().when(steps);
    }

    /**
     * Describes a single {@link Step} and returns it as {@link Steps}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return An instance of {@link Steps} with the specified {@link Step} inside.
     */
    public static Steps when(String description, Runnable runner)
    {
        return new Steps().when(description, runner);
    }

}
