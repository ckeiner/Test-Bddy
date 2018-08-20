package bddtester.core.throwables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.MultipleFailureException;

import bddtester.core.throwables.errors.ScenarioError;
import bddtester.core.throwables.exceptions.ScenarioException;

/**
 * Wraps multiple {@link ScenarioException}s.
 * 
 * @author ckeiner
 */
public class MultipleScenarioWrapperException extends MultipleFailureException
{
    private static final long serialVersionUID = 58234950048201778L;

    /**
     * Creates a new instance of this class by calling
     * {@link MultipleScenarioWrapperException#MultipleScenarioWrapperException(List)}
     * with an empty list.
     */
    public MultipleScenarioWrapperException()
    {
        this(new ArrayList<ScenarioException>());
    }

    /**
     * Converts the argument to a List of Throwables, then calls the super
     * constructor {@link MultipleFailureException#MultipleFailureException(List)}.
     * 
     * @param errors
     *            A list of {@link ScenarioException}s
     */
    public MultipleScenarioWrapperException(List<ScenarioException> exceptions)
    {
        super(init(exceptions));
    }

    /**
     * Combines a list of errors and exceptions, then calls the super constructor
     * {@link MultipleFailureException#MultipleFailureException(List)}.
     * 
     * @param exceptions
     *            A list of {@link ScenarioException}s
     * @param errors
     *            A list of {@link ScenarioError}s
     */
    public MultipleScenarioWrapperException(List<ScenarioException> exceptions, List<ScenarioError> errors)
    {
        super(init(exceptions, errors));
    }

    /**
     * Converts the list of exceptions to a list of Throwables
     * 
     * @param exceptions
     *            A list of {@link ScenarioException}s
     * @return A list of Throwables
     */
    private static List<Throwable> init(List<ScenarioException> exceptions)
    {
        List<Throwable> throwables = new ArrayList<>();
        for (ScenarioException exception : exceptions)
        {
            throwables.add(exception);
        }
        return throwables;
    }

    /**
     * First, it adds all exceptions to a list of Throwables, then it adds all
     * errors to the list.
     * 
     * @param exceptions
     *            A list of {@link ScenarioException}s
     * @param errors
     *            A list of {@link ScenarioError}s
     * @return A list of Throwables
     */
    private static List<Throwable> init(List<ScenarioException> exceptions, List<ScenarioError> errors)
    {
        List<Throwable> throwables = new ArrayList<>();
        throwables.addAll(exceptions);
        throwables.addAll(errors);
        return throwables;
    }

    /**
     * Prints the stack trace of every Throwable to the specified
     * {@link PrintStream}
     * 
     * @param out
     *            The PrintStream to print the stack trace to
     */
    @Override
    public void printStackTrace(PrintStream out)
    {
        int i = 0;
        for (Throwable t : getFailures())
        {
            out.println("Error " + i + ": " + t.getMessage());
            t.printStackTrace(out);
            out.println();
            i++;
        }
    }

    /**
     * Returns a String with the stack trace of every Throwable.<br>
     * Needed by JUnit and ExtentReports to display the error or exception.
     */
    @Override
    public String getMessage()
    {
        StringBuilder sb = new StringBuilder(String.format("There were %d errors:", getFailures().size()));
        // Iterate over all throwables
        for (Throwable e : getFailures())
        {
            // Print the minimal information
            sb.append(String.format("\n  %s(%s)", e.getClass().getName(), e.getMessage()));
            // Get the content of the Throwable
            String content = stackTrace(e);
            // Append the content of the stack trace to the string builder
            sb.append("\n  " + content);
        }
        // Return the String in String Builder
        return sb.toString();
    }

    /**
     * Returns the stack trace of the Throwable as String.
     * 
     * @param throwable
     *            The Throwable you want the stack trace to
     * @return A String containing the stack trace
     */
    private String stackTrace(Throwable throwable)
    {
        final Charset charset = StandardCharsets.UTF_8;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = null;
        // Try to create the Print Stream
        try
        {
            ps = new PrintStream(baos, true, charset.name());
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        // Write stack trace to the print stream
        printStackForOneError(throwable, ps);
        // Extract the stack trace from the print stream/ByteArrayOutputStream
        String content = new String(baos.toByteArray(), charset);
        // Close the print stream
        ps.close();
        // Close the ByteArrayOutputStream
        try
        {
            baos.close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
        // Return the content
        return content;
    }

    /**
     * Prints the stack trace of a single Throwable to the specified
     * {@link PrintStream}.
     * 
     * @param error
     *            The Throwable with the stack trace to print
     * @param out
     *            The writer to print the stack trace to
     */
    private void printStackForOneError(Throwable error, PrintStream out)
    {
        int i = getFailures().indexOf(error) + 1;
        out.println("Error " + i + ": " + error.getMessage());
        error.printStackTrace(out);
        out.println();
    }

}
