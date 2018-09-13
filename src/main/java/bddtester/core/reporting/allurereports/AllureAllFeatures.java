package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureAllFeatures
{
    List<AllureFeature> allFeature;
    String description;

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
