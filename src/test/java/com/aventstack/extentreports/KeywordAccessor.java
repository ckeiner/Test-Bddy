package com.aventstack.extentreports;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;

public class KeywordAccessor
{
    public static IGherkinFormatterModel getKeyword(GherkinKeyword keyword)
    {
        return keyword.getKeyword();
    }

}
