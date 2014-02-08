package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.ScenarioType;
 
public class JBExampleSteps {
    int x;
    int strLen = 0;
    String str;

    @BeforeScenario
    public void beforeEachScenario(@Named("steps") String steps) {
        if (steps.contains("BasicSteps"))  
        System.out.println("Before Basic Scenario ...");
    }
 
    @BeforeScenario(uponType=ScenarioType.EXAMPLE)
    public void beforeEachExampleScenario(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("Before Basic Each Example Scenario ...");
    }
     
    @AfterScenario // equivalent to  @AfterScenario(uponOutcome=AfterScenario.Outcome.ANY)
    public void afterAnyScenario(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("After Basic Any Scenario ...");
    }
 
    @AfterScenario(uponType=ScenarioType.EXAMPLE)
    public void afterEachExampleScenario(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("After Basic Each Example Scenario ...");
    }
     
    @AfterScenario(uponOutcome=AfterScenario.Outcome.SUCCESS)
    public void afterSuccessfulScenario(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("After Basic Successful Scenario ...");
    }
     
    @AfterScenario(uponOutcome=AfterScenario.Outcome.FAILURE)
    public void afterFailedScenario(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("After Basic Failed Scenario ...");
    }

    @BeforeStory // equivalent to @BeforeStory(uponGivenStory=false)
    public void beforeStory(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("Before Basic Story ...");
    }
 
    @BeforeStory(uponGivenStory=true)
    public void beforeGivenStory(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("Before Basic Given Story ...");
    }
     
    @AfterStory // equivalent to @AfterStory(uponGivenStory=false)
    public void afterStory(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("After Basic Story ...");
    }
 
    @AfterStory(uponGivenStory=true)
    public void afterGivenStory(@Named("steps") String steps) {
        if (steps.contains("BasicSteps")) 
        System.out.println("After Basic Given Story ...");
    }

	@Given("a string '$str'")
	public void givenStr(@Named("str") String value) {
		str = value;
		strLen = str.length();
	}

	@Then("string length should equal '$value'")
	public void strLengthShouldBe(@Named("value") int value) {
		if (value != strLen)
			throw new RuntimeException("strLen is " + strLen + ", but should be " + value);
	}
 
	@Given("a variable x with value $value")
	public void givenXValue(@Named("value") int value) {
		x = value;
	}
 
	@When("I multiply x by $value")
	public void whenImultiplyXBy(@Named("value") int value) {
		x = x * value;
	}
 
	@Then("x should equal $value")
	public void thenXshouldBe(@Named("value") int value) {
		if (value != x)
			throw new RuntimeException("x is " + x + ", but should be " + value);
	}
}
