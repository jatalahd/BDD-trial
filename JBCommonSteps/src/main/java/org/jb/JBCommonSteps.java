package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;

import java.util.TreeMap;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
 
public class JBCommonSteps {

    private TreeMap<String,String> varMap;

    public JBCommonSteps() {
        varMap = new TreeMap<String,String>();
    }

    @Given("a global variable $varName with value $varValue")
    public void addVariable(final String varName, final String varValue) {
        String value = this.replaceVariable(varValue);
        varMap.put(varName, value);
    }

    @Given("replace a variable with its value in $string")
    public String replaceVariable(final String str) {
        String replaced = str;
        for (String item : varMap.descendingKeySet()) {
             if (replaced.contains(item)) {
                 replaced = replaced.replace(item, varMap.get(item));
             }
        }
        System.out.println(replaced);
        return replaced;
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

    @When("Groovy script $script is executed")
    public Object executeGroovyScript(final String scriptStr) {
        Binding binding = new Binding();
        binding.setVariable("commonSteps", this);
        GroovyShell shell = new GroovyShell(binding);
        return shell.evaluate(scriptStr);
    }
}
