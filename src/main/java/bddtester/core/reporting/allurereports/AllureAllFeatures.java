package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureAllFeatures
{
    private List<AllureFeature> allFeature;
    private String description;
    private String allureActualElement;

    public String getAllureActualElement()
    {
        return allureActualElement;
    }

    public void setAllureActualElement(String allureListType)
    {
        this.allureActualElement = allureListType;
    }

    public List<AllureFeature> getAllFeature()
    {
        return allFeature;
    }

    public void setAllFeature(List<AllureFeature> allFeature)
    {
        this.allFeature = allFeature;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public AllureAllFeatures()
    {
        this.allFeature = new ArrayList<AllureFeature>();
    }

    public void add(String feature)
    {
        allFeature.add(new AllureFeature(feature));
    }

    public AllureFeature getLastFeature()
    {
        return allFeature.get(allFeature.size() - 1);
    }
}
