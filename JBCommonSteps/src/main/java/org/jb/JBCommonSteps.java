package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;

import java.util.TreeMap;
 
public class JBCommonSteps {

    private TreeMap<String,String> varMap;

    public JBCommonSteps() {
        varMap = new TreeMap<String,String>();
    }

    @Given("a global variable $varName with value $varValue")
    public void addVariable(final String varName, final String varValue) {
        varMap.put(varName, varValue);
    }

    @Then("print the value of global variable $varName")
    public void printVariable(final String varName) {
        System.out.println(varMap.get(varName));
    }    

    @When("global variable map is printed iteratively")
    public void iterativePrint() {
        for (String item : varMap.descendingKeySet()) {
            System.out.println(varMap.get(item));
        }
    }
}
