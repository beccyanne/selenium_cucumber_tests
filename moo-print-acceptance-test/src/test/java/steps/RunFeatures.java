package steps;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by deeptik on 11/03/2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features/", plugin = "json:target/cucumber/cucumber.json")
public class RunFeatures {

}
