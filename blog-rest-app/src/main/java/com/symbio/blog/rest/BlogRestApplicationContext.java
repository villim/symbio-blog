package com.symbio.blog.rest;

import com.google.gson.Gson;
import com.symbio.blog.infrastructure.PersistenceJPAContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("blog-rest-context")
//@Import({PersistenceJPAContext.class, HazelcastHttpSessionConfig.class})
@Import({PersistenceJPAContext.class})
@EnableJpaRepositories(basePackages = {"com.symbio.blog.infrastructure.springdata"})
@ComponentScan(basePackages = {"com.symbio.blog.domain", "com.symbio.blog.service", "com.symbio.blog.infrastructure", "com.symbio.blog.rest"})
@PropertySource({"file:/opt/symbio/config/blog/blog-public.properties", "classpath:version.properties"})
public class BlogRestApplicationContext {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
