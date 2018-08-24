package tests;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Assert;
import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;

public class ConsumingCucumbers extends AbstractExtentReportTest
{
    /**
     * Verifies the tags in a feature work as intended.
     */
    @Test
    public void whenIEatCucumbers()
    {
        //@formatter:off
        feature("Eating Cucumbers",
            () -> scenario("Eating certain cucumbers",
                    withData(new Testdata(10, 3, 7))
                    .given("I have <cucumbers> cucumbers", data -> {
                        data.eater.setCucumbers(data.cucumbers);
                    })
                    .when("I eat <eaten> cucumbers", data -> {
                        data.eater.eat(data.eaten);
                    })
                    .then("I have <left> cucumbers left", data -> {
                        Assert.assertNotEquals(data.left, data.eater.getCucumbers());
                    })
            )
        ).test();
        //@formatter:on
    }

    protected class Testdata
    {
        public int cucumbers;

        public int eaten;

        public int left;

        CucumberEater eater;

        public Testdata(int cucumbers, int eaten, int left)
        {
            this.cucumbers = cucumbers;
            this.eaten = eaten;
            this.left = left;
            this.eater = new CucumberEater();
        }

        @Override
        public String toString()
        {
            return "Have: " + cucumbers + ", Eat: " + eaten + ", Left: " + left;
        }

    }

    protected class CucumberEater
    {
        private Integer cucumbers;

        public CucumberEater()
        {
            cucumbers = null;
        }

        public CucumberEater(int cucumbers)
        {
            this.cucumbers = cucumbers;
        }

        public void eat(int numberEaten)
        {
            this.cucumbers = this.cucumbers - numberEaten;
        }

        public int getCucumbers()
        {
            return cucumbers;
        }

        public void setCucumbers(Integer cucumbers)
        {
            this.cucumbers = cucumbers;
        }
    }
}
