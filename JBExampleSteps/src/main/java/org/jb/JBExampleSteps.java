package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
 
public class JBExampleSteps {
	int x;
	int strLen = 0;
	String str;

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
