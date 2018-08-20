package bddtester.core.throwables.exceptions;

import bddtester.core.bdd.steps.Step;

/**
 * Describes an exception that happens in a {@link Step}.
 * 
 * @author ckeiner
 */
public class StepException extends RuntimeException
{
    private static final long serialVersionUID = 453433579352686803L;

    public StepException()
    {
        super();
    }

    public StepException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public StepException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public StepException(String message)
    {
        super(message);
    }

    public StepException(Throwable cause)
    {
        super(cause);
    }

}
