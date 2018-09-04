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

    /**
     * @see RuntimeException#RuntimeException()
     */
    public StepException()
    {
        super();
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    public StepException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public StepException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public StepException(String message)
    {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public StepException(Throwable cause)
    {
        super(cause);
    }

}
