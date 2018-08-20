package bddtester.core.throwables.errors;

import bddtester.core.bdd.steps.Step;
import bddtester.core.bdd.steps.Steps;
import bddtester.core.bdd.steps.TypeStep;
import bddtester.core.bdd.steps.TypeSteps;

//TODO too implementation specific?
/**
 * Describes an Error that happens in a {@link Step}, thus in {@link Steps} or
 * {@link TypeStep}, thus in {@link TypeSteps}.
 * 
 * @author ckeiner
 */
public class StepError extends Error
{
    private static final long serialVersionUID = 453433579352686803L;

    public StepError()
    {
        super();
    }

    public StepError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public StepError(String message, Throwable cause)
    {
        super(message, cause);
    }

    public StepError(String message)
    {
        super(message);
    }

    public StepError(Throwable cause)
    {
        super(cause);
    }

}
