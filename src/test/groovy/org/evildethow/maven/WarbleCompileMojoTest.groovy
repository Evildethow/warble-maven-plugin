package org.evildethow.maven

import org.jruby.embed.AttributeName
import org.jruby.embed.PathType
import org.jruby.embed.ScriptingContainer
import org.junit.Before
import org.junit.Test

class WarbleCompileMojoTest {

    WarbleCompileMojo mojo

    @Before
    void init() {
        mojo = new WarbleCompileMojo()
    }

    @Test
    void assertSlurp() {
        def baseDir = new File( "src/test/test-project/src/main/jruby/" )
        def result = []
        baseDir.eachFileRecurse {
            if (it.path =~ /.*\.rb/) {
                result << it
            }
        }

        result.each { File file -> println(file.absolutePath)}
    }

    @Test
    public void assertCopyDirectory() {
        def sourceDir = '/home/woodo/IdeaProjects/warble-maven-plugin/src/test/test-project/src/main/jruby'
        String destinationDir = '/home/woodo/IdeaProjects/warble-maven-plugin/src/test/test-project/target/classes'


        new AntBuilder().copy(todir: destinationDir) {
            fileset(dir : sourceDir) {
                include(name:"**/*.rb")
            }
        }

    }

    /**
     * Seems to be the best bet so far
     */
    @Test
    public void assertScriptingContainer() {
        ScriptingContainer container = new ScriptingContainer();
        container.setAttribute(AttributeName.BASE_DIR, System.getProperty("user.dir"));
        container.setLoadPaths(Arrays.asList("/home/woodo/IdeaProjects/warble-maven-plugin/target/test-classes/gems/warbler-1.3.8/lib", "/home/woodo/IdeaProjects/warble-maven-plugin/target/test-classes/gems/rake-10.1.0/lib", "/home/woodo/IdeaProjects/warble-maven-plugin/src/test/groovy/org/evildethow/maven/bin"))
        container.runScriptlet(PathType.RELATIVE, "src/test/groovy/org/evildethow/maven/warbler_test_invoker.rb");
    }

}
