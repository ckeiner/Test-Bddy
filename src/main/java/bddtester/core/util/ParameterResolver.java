package bddtester.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;

public class ParameterResolver<T>
{
    public static Interpreter interpreter = new Interpreter();

    public String resolvePlaceholders(String description, T testdata)
    {
        String output = description;
        Matcher matcher = Pattern.compile("<.*>").matcher(description);
        while (matcher.find())
        {
            String match = matcher.group().replaceAll("<", "").replaceAll(">", "");
            String resolvedValue = resolvePlaceholder(match, testdata);
            output = output.replace("<" + match + ">", resolvedValue);
        }
        return output;
    }

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
        return output;
    }

}
