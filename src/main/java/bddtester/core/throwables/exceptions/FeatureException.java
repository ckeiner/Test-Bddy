package bddtester.core.throwables.exceptions;

import bddtester.core.bdd.Feature;

/**
 * Describes an exception that happens in a {@link Feature}.
 * 
 * @author ckeiner
 */
public class FeatureException extends RuntimeException
{
    private static final long serialVersionUID = 453433579352686803L;

    public FeatureException()
    {
        super();
    }

    public FeatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FeatureException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FeatureException(String message)
    {
        super(message);
    }

    public FeatureException(Throwable cause)
    {
        super(cause);
    }

}
