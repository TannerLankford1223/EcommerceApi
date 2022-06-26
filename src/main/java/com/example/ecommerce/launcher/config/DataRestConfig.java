package com.example.ecommerce.launcher.config;


import com.example.ecommerce.infrastructure.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Type;
import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @Autowired
    public DataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // configure cors mapping
        cors.addMapping(basePath + "/**").allowedOrigins(allowedOrigins);
        // disable HTTP methods for Product: Put, Post, Delete
        disableHTTPMethods(Product.class, config, unsupportedActions);
        // disable HTTP methods for ProductCategory: Put, Post, Delete
        disableHTTPMethods(ProductCategory.class, config, unsupportedActions);
        // disable HTTP methods for Country/State: Put, Post, Delete
        disableHTTPMethods(Country.class, config, unsupportedActions);
        disableHTTPMethods(State.class, config, unsupportedActions);
        // disable HTTP methods for Order: Put, Post, Delete
        disableHTTPMethods(Order.class, config, unsupportedActions);

        // call an internal helper method to help us expose the id's
        exposeIds(config);
    }

    private void disableHTTPMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)));
    }

    // expose entity ids
    private void exposeIds(RepositoryRestConfiguration config) {
        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        Class[] domainTypes = entities.stream().map(Type::getJavaType).toArray(Class[]::new);

        // - expose the entity ids for the array of entity/domain types
        config.exposeIdsFor(domainTypes);
    }
}
