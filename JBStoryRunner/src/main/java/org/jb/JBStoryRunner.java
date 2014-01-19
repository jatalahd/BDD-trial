package org.jb;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
 
import org.jb.JBExampleSteps;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.MetaFilter;
 
public class JBStoryRunner extends JUnitStories {
 
    public JBStoryRunner() {
        super();
        configuredEmbedder().useMetaFilters(Arrays.asList("-skip"));
    }

    @Override
    public Configuration configuration() {
        Configuration config = new MostUsefulConfiguration();
        config.useParameterControls(new ParameterControls().useDelimiterNamedParameters(true));
        config.useStoryReporterBuilder( new StoryReporterBuilder().withDefaultFormats()
                                                  .withFormats(Format.HTML,Format.TXT)
                                                  .withFailureTrace(true)
                                                  .withFailureTraceCompression(true)
                                                  .withRelativeDirectory("my-output")
                                                  .withCodeLocation(CodeLocations.codeLocationFromClass( this.getClass() ) ) );
        return config;                      
    }
 
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new JBExampleSteps(), new JBWebDriverSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "");
    }
}
