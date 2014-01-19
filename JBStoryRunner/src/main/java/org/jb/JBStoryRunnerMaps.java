package org.jb;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
 
import org.jb.JBExampleSteps;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.junit.JUnitStoryMaps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TemplateableViewGenerator;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.MetaFilter;
 
public class JBStoryRunnerMaps extends JUnitStoryMaps {
 
    public JBStoryRunnerMaps() {
        super();
        configuredEmbedder().useMetaFilters(metaFilters());
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryReporterBuilder( 
                        new StoryReporterBuilder().withDefaultFormats()
                                                  .withFailureTraceCompression(true)
                                                  .withRelativeDirectory("my-output")
                                                  .withCodeLocation(CodeLocations.codeLocationFromClass( this.getClass() ) ) );
    }
 
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new JBExampleSteps());
    }

    @Override
    protected List<String> metaFilters() {
        return Arrays.asList("-skip");
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "");
    }
}
