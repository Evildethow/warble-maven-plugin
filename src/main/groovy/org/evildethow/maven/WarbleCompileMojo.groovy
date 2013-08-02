package org.evildethow.maven

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.jruby.embed.PathType
import org.jruby.embed.ScriptingContainer

/**
 * Compiles JRuby (.rb) files to compiled Java (.class) files using warble.
 *
 * The current conventional approach using this plugin to supplement an application
 * running on the JVM would either be as a separate module within a multi-module
 * project as a dependency or as a standalone maven project used as a maven (jar)
 * dependency, typically provided via a proxy maven repository.
 *
 * @author evildethow
 */
@SuppressWarnings(["GroovyUnusedDeclaration","GrMethodMayBeStatic"])
@Mojo(name = "compile", defaultPhase = LifecyclePhase.COMPILE)
public class WarbleCompileMojo extends AbstractMojo {

    /**
     * Uses ${basedir}/src/main/jruby by default.
     */
    @Parameter( property = "project.build.jruby.sourceDirectory", defaultValue = '${basedir}/src/main/jruby')
    private File sourceDirectory

    /**
     * Directory containing the generated .class files.
     */
    @Parameter( property = "project.build.jruby.buildDirectory", defaultValue = '${project.build.directory}')
    private File buildDirectory

    @Override
    void execute() {
        logSourceRoots(sourceDirectory, log)
        copySrcToTarget()
        compile()
    }

    def logSourceRoots(file, log) {
        log.debug('Source roots:')
        log.debug('   ' + file.absolutePath)
    }

    def copySrcToTarget() {
        new AntBuilder().copy(todir: buildDirectory.absolutePath) {
            fileset(dir : sourceDirectory.absolutePath) {
                include(name:"**/*.rb")
            }
        }
    }

    def compile() {
        log.info('Compiling .rb to .class')
        ScriptingContainer container = new ScriptingContainer()
        container.setLoadPaths(Arrays.asList("/home/woodo/IdeaProjects/warble-maven-plugin/target/test-classes/gems/warbler-1.3.8/lib", "/home/woodo/IdeaProjects/warble-maven-plugin/target/test-classes/gems/rake-10.1.0/lib", "/home/woodo/IdeaProjects/warble-maven-plugin/src/test/groovy/org/evildethow/maven/bin"))
        container.runScriptlet(PathType.CLASSPATH, "warbler_compile_invoker.rb")
    }

}
