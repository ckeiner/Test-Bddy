package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureFeature extends AbstractAllureListType
{
    private List<AllureScenario> feature;
    private String description;

    public List<AllureScenario> getFeature()
    {
        return feature;
    }

    public void setFeature(List<AllureScenario> feature)
    {
        this.feature = feature;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public AllureFeature(String description)
    {
        this.feature = new ArrayList<AllureScenario>();
        this.description = description;
    }

    public void add(String scenario)
    {
        feature.add(new AllureScenario(scenario));
    }

    public AllureScenario getLastScenario()
    {
        return feature.get(feature.size() - 1);
    }
}
