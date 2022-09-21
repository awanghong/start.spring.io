package io.spring.start.site.extension.demo.mybatis;

import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.text.MustacheSection;
import io.spring.initializr.generator.language.*;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.code.TestApplicationTypeCustomizer;
import io.spring.initializr.generator.spring.code.TestSourceCodeCustomizer;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import io.spring.initializr.generator.spring.util.MavenModuleUtil;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author 王红
 * @date2022/9/19 18:10
 * @description
 */
public class MybatisDemoContributor implements ProjectContributor {

    public static final String name = "mybatis";

    private final MustacheTemplateRenderer renderer = new MustacheTemplateRenderer("classpath:/templates/mybatis");

    private final ProjectDescription description;

    private final InitializrMetadata metadata;


    public MybatisDemoContributor(ProjectDescription description, InitializrMetadata metadata) {
        this.description = description;
        this.metadata = metadata;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        String architecture = obtainArchitecture();
        List<String> demos = this.description.getRequestedDemos();
        if (MavenModuleUtil.MVC_ARCHITECTURE.equals(architecture) && demos.contains(name)) {
            multiModuleCreateAndFill(projectRoot);
        }
    }

    private void multiModuleCreateAndFill(Path projectRoot) throws IOException {
        String model = this.description.getName() + "-model";
        createModule(model, projectRoot);
    }


    private String obtainArchitecture() {
        String architecture = this.description.getArchitecture();
        String aDefault = this.metadata.getArchitectures().getDefault().getId();
        architecture = (architecture != null) ? architecture : aDefault;
        architecture = (architecture != null) ? architecture : MavenModuleUtil.MVC_ARCHITECTURE;
        return architecture;
    }


    private void createModule(String moduleName, Path projectRoot) throws IOException {
        String name = "MybatisDemoUser";
        SourceStructure sourceStructure = new SourceStructure(projectRoot.resolve(moduleName + "/src/main/"), this.description.getLanguage());
        String packageName = this.description.getPackageName() + ".mybatis.entity";
        Path output = sourceStructure.createSourceFile(packageName, name);
        Files.createDirectories(output.getParent());

        String code = renderer.render("mybatis-demo-user-controller", Collections.singletonMap("package", packageName));
        Files.write(output, code.getBytes("UTF-8"));

    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 11;
    }
}
