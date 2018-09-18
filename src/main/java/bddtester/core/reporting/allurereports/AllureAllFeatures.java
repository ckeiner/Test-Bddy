package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureAllFeatures
{
    private List<AllureFeature> allureFeatures;

    private AbstractAllureListType actualElement;

    public AllureAllFeatures()
    {
        this.allureFeatures = new ArrayList<AllureFeature>();
    }

    public AbstractAllureListType getActualElement()
    {
        return actualElement;
    }

    public void setActualElement(AbstractAllureListType actualElement)
    {
        this.actualElement = actualElement;
    }

    public List<AllureFeature> getAllFeature()
    {
        return allureFeatures;
    }

    public void add(String description)
    {
        allureFeatures.add(new AllureFeature(description));
    }

    public AllureFeature getLastFeature()
    {
        return allureFeatures.get(allureFeatures.size() - 1);
    }
}
