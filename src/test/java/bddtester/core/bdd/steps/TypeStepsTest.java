package bddtester.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.KeywordAccessor;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.ckeiner.testbddy.core.bdd.steps.TypeStep;
import com.ckeiner.testbddy.core.bdd.steps.TypeSteps;

public class TypeStepsTest
{
    /**
     * Verifies that {@link TypeSteps} can contain no {@link TypeStep}s
     */
    @Test
    public void canContainNoStep()
    {
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Assert there is an empty list of TypeStep
        Assert.assertTrue(steps.getSteps().isEmpty());
    }

    /**
     * Verifies that {@link TypeSteps} can contain no {@link TypeStep}s
     */
    @Test
    public void canContainEmptyStep()
    {
        // Create list of steps
        List<TypeStep<Object>> stepList = new ArrayList<TypeStep<Object>>();
        // Create instance of steps with stepList
        TypeSteps<Object> steps = new TypeSteps<Object>(stepList);
        // Assert there is an empty list of TypeStep
        Assert.assertTrue(steps.getSteps().isEmpty());
    }

    /**
     * Verifies that {@link TypeSteps} can contain a {@link TypeStep} with
     * {@link TypeSteps#given(String, Runnable)}.
     */
    @Test
    // TODO How to handle this case?
    public void canContainAStepWithNullList()
    {
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>(null);
        Assert.assertNull(steps.getSteps());

        String stepDescription = "A given step";
        // Add a given step
        steps.given(stepDescription, (Runnable) null);
        // Assert that the list isn't null anymore
        Assert.assertTrue(steps.getSteps() != null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link TypeSteps} can contain a {@link TypeStep} with
     * {@link TypeSteps#given(String, Runnable)}.
     */
    @Test
    public void canContainAGivenStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link TypeSteps} can contain a {@link TypeStep} with
     * {@link TypeSteps#when(String, Runnable)}.
     */
    @Test
    public void canContainAWhenStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a when step
        steps.when(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof When);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link TypeSteps} can contain a {@link TypeStep} with
     * {@link TypeSteps#and(String, Runnable)}.
     */
    @Test
    public void canContainAnAndStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a and step
        steps.and(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof And);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link TypeSteps} can contain a {@link TypeStep} with
     * {@link TypeSteps#then(String, Runnable)}.
     */
    @Test
    public void canContainAThenStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a then step
        steps.then(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Then);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

}
