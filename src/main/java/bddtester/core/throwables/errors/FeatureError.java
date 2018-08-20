package bddtester.core.throwables.errors;

import bddtester.core.bdd.Feature;

/**
 * Describes an error that happens in a {@link Feature}.
 * 
 * @author ckeiner
 */
public class FeatureError extends AssertionError
{
    private static final long serialVersionUID = 453433579352686803L;

    public FeatureError()
    {
        super();
    }

    public FeatureError(boolean detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(char detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(double detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(float detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(int detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(long detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(Object detailMessage)
    {
        super(detailMessage);
    }

    public FeatureError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
