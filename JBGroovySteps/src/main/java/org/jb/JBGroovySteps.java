package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
 
public class JBGroovySteps {

    private Binding binds = null;
    private GroovyShell shell = null;

    public JBGroovySteps(JBCommonSteps    common,
                         JBExampleSteps   example,
                         JBWebDriverSteps driver) {
    binds = new Binding();
    binds.setVariable("commonSteps", common);
    binds.setVariable("exampleSteps", example);
    binds.setVariable("webDriverSteps", driver);
    shell = new GroovyShell(binds);
    }

    @When("Groovy script $script is executed")
    public Object executeGroovyScript(final String scriptStr) {
        return shell.evaluate(scriptStr);
    }
}
