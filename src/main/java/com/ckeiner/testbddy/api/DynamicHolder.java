package com.ckeiner.testbddy.api;

public class DynamicHolder<T>
{
    T content;

    public DynamicHolder()
    {
    }

    public T get()
    {
        return content;
    }

    public void set(T newContent)
    {
        content = newContent;
    }
}