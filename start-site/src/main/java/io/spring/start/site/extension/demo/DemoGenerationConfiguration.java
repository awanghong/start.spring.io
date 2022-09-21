package io.spring.start.site.extension.demo;

import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.start.site.extension.demo.mybatis.MybatisDemoContributor;
import org.springframework.context.annotation.Bean;

/**
 * @author 王红
 * @date2022/9/19 18:16
 * @description
 */
@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(MavenBuildSystem.ID)
public class DemoGenerationConfiguration {

    @Bean
    @ConditionalOnRequestedDependency(MybatisDemoContributor.name)
    public MybatisDemoContributor mybatisDemoContributor(ProjectDescription description, InitializrMetadata metadata) {
        return new MybatisDemoContributor(description, metadata);
    }

}
