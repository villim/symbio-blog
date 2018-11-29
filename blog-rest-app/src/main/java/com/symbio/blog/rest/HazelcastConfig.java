package com.symbio.blog.rest;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.symbio.blog.rest.spring.ObjectStreamSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.util.SocketUtils;


@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = 300) // 5 mins
@Configuration
public class HazelcastConfig {

    private static final Logger LOG = LoggerFactory.getLogger(HazelcastConfig.class);


    @Bean(name = "blogHazelcastInstance", destroyMethod = "shutdown")
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();

        int port = SocketUtils.findAvailableTcpPort();

        config.getNetworkConfig().setPort(port);

        LOG.debug("Hazelcast port #: {} ", port);

        SerializerConfig serializer = new SerializerConfig()
                .setImplementation(new ObjectStreamSerializer())
                .setTypeClass(Object.class);

        config.getSerializationConfig().addSerializerConfig(serializer);

        MapAttributeConfig attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());

        config.getMapConfig("spring:session:sessions")
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        // Can change current header name "x-auth-token"
        // HeaderHttpSessionStrategy headerHttpSessionStrategy = new HeaderHttpSessionStrategy();
        // headerHttpSessionStrategy.setHeaderName("Authorization");
        // return headerHttpSessionStrategy;
        return new HeaderHttpSessionStrategy();
    }

}
