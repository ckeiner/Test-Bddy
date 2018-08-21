package bddtester.core.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

public class ReflectionResolver<T>
{
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
        Iterable<Field> fields = ReflectionResolver.getFieldsUpTo(testdata.getClass(), Object.class);
        for (Field field : fields)
        {
            if (placeholder.equals(field.getName()))
            {
                try
                {
                    Object value = field.get(testdata);
                    output = value.toString();
                } catch (IllegalArgumentException | IllegalAccessException e)
                {
                    System.out.println("Cannot access specified field. Ignoring...");
                }
                break;
            }
        }
        return output;
    }

    private static Iterable<Field> getFieldsUpTo(@Nonnull Class<?> startClass, @Nullable Class<?> exclusiveParent)
    {

        List<Field> currentClassFields = Lists.newArrayList(startClass.getDeclaredFields());
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null && (exclusiveParent == null || !(parentClass.equals(exclusiveParent))))
        {
            List<Field> parentClassFields = (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }

}
