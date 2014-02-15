package org.jb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.runner.RunWith;

import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.ConfigurableEmbedder;
 
public class JBStoryRunner extends JUnitStories {
 
    public JBStoryRunner() {
        super();
        configuredEmbedder().useMetaFilters(getMetaTags());
    }

    @Override
    public Configuration configuration() {
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        Configuration config = new MostUsefulConfiguration();
        config.useParameterControls(new ParameterControls().useDelimiterNamedParameters(true));
        config.useStoryReporterBuilder( new StoryReporterBuilder().withDefaultFormats()
                                                  .withFormats(Format.CONSOLE, Format.HTML, Format.XML, Format.STATS)
                                                  .withViewResources(viewResources)
                                                  .withFailureTrace(true)
                                                  .withFailureTraceCompression(true)
                                                  /*.withRelativeDirectory("my-output")*/
                                                  .withCodeLocation(CodeLocations.codeLocationFromClass( this.getClass() ) ) );
        return config;                      
    }
 
    @Override
    public InjectableStepsFactory stepsFactory() {
        JBCommonSteps    jbc = new JBCommonSteps();
        JBExampleSteps   jbe = new JBExampleSteps();
        JBWebDriverSteps jbw = new JBWebDriverSteps();
        JBGroovySteps    jbg = new JBGroovySteps(jbc, jbe, jbw);
        return new InstanceStepsFactory(configuration(), jbc, jbe, jbw, jbg);
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "");
    }

    private List<String> getMetaTags() {
        List<String> arr = new ArrayList<String>();
        arr.add("-skip");
        String s = "";
        if (System.getProperty("meta") != null) {
            s = System.getProperty("meta");
        }
        arr.add(s.contains("+") ? s + " +givenStory" : s);
        return arr;
    }
}
