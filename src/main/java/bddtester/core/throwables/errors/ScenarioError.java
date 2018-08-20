package bddtester.core.throwables.errors;

/**
 * Describes an error that happens in a {@link Scenario}.
 * 
 * @author ckeiner
 */
public class ScenarioError extends Error
{
    private static final long serialVersionUID = 453433579352686803L;

    public ScenarioError()
    {
        super();
    }

    public ScenarioError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ScenarioError(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ScenarioError(String message)
    {
        super(message);
    }

    public ScenarioError(Throwable cause)
    {
        super(cause);
    }

}
