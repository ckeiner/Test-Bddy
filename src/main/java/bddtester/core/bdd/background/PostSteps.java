package bddtester.core.bdd.background;

import java.util.ArrayList;
import java.util.List;

public class PostSteps
{
    private final List<PostStep> postSteps;

    public PostSteps()
    {
        this(new ArrayList<>());
    }

    public PostSteps(List<PostStep> postSteps)
    {
        this.postSteps = postSteps;
    }

    public void addPostStep(PostStep postStep)
    {
        getPostSteps().add(postStep);
    }

    public List<PostStep> getPostSteps()
    {
        return postSteps;
    }

}
