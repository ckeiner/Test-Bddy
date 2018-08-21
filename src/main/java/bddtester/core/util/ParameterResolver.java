package bddtester.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Resolves any placeholder by using beanshell.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the object of which the placeholder is a field or
 *            method.
 */
public class ParameterResolver<T>
{
    public static Interpreter interpreter = new Interpreter();

    /**
     * The String with which placeholders start
     */
    public final static String ENCLOSE_START = "<";

    /**
     * The String with which placeholders end
     */
    public final static String ENCLOSE_END = ">";

    /**
     * Resolves any placeholder thats enclosed by {@value #ENCLOSE_START} and
     * {@value #ENCLOSE_END}.
     * 
     * @param description
     *            The String which should be checked for placeholders.
     * @param testdata
     *            The instance of which the placeholder is a field or method.
     * @return A String where all placeholders are resolved if a field or method
     *         like that is found. <br>
     *         If none is found, the placeholder is inserted again.
     */
    public String resolvePlaceholders(String description, T testdata)
    {
        String output = description;
        Matcher matcher = Pattern.compile(ENCLOSE_START + ".*" + ENCLOSE_END).matcher(description);
        while (matcher.find())
        {
            String match = matcher.group().replaceAll(ENCLOSE_START, "").replaceAll(ENCLOSE_END, "");
            String resolvedValue = resolvePlaceholder(match, testdata);
            output = output.replace(ENCLOSE_START + match + ENCLOSE_END, resolvedValue);
        }
        return output;
    }

    /**
     * Resolves the placeholder by asking the testdata object for it.
     * 
     * @param placeholder
     *            The placeholder without the enclosing characters.
     * @param testdata
     *            The instance of which the placeholder is a field or method.
     * @return A String where the placeholders are resolved if a field or method
     *         like that is found. <br>
     *         If none is found, the placeholder is returned with its enclosing
     *         characters again.
     */
    private String resolvePlaceholder(String placeholder, T testdata)
    {
        String output = placeholder;
        String testdataPrefix = "$data";
        try
        {
            interpreter.set(testdataPrefix, testdata);
            String statement = testdataPrefix + "." + placeholder;
            Object value = interpreter.eval(statement);
            output = value.toString();
        } catch (EvalError e)
        {
            e.printStackTrace();
        }
        // If we didn't find anything
        if (output == null || output.isEmpty())
        {
            output = ENCLOSE_START + placeholder + ENCLOSE_END;
        }
        return output;
    }

}
