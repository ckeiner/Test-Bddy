package bddtester.core.bdd.steps;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.ckeiner.testbddy.core.bdd.steps.Step;

public class StepTest
{
    /**
     * Verifies that a {@link Step} can contain no behavior.
     * 
     * @throws ClassNotFoundException
     */
    @Test
    public void canContainNoBehavior() throws ClassNotFoundException
    {
        String keywordString = "Given";
        // Create instance of steps
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        String stepDescription = "Step Description";
        Step step = new Step(keyword, stepDescription, null);
        // Assert the keyword is correct
        Assert.assertEquals(keyword, step.getKeyword());
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is correct
        Assert.assertNull(step.getBehavior());
    }

}
