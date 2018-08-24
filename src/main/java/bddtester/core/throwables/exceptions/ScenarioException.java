package bddtester.core.throwables.exceptions;

import bddtester.core.bdd.scenario.Scenario;

/**
 * Describes an exception that happens in a {@link Scenario}.
 * 
 * @author ckeiner
 */
public class ScenarioException extends RuntimeException
{
    private static final long serialVersionUID = 453433579352686803L;

    public ScenarioException()
    {
        super();
    }

    public ScenarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ScenarioException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ScenarioException(String message)
    {
        super(message);
    }

    public ScenarioException(Throwable cause)
    {
        super(cause);
    }

}
