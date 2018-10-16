package com.philips.backend.repository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import com.philips.backend.dao.Location;
import com.philips.backend.dao.User;


@Configuration
public class RepositroriesConfiguration extends RepositoryRestConfigurerAdapter {

 @Override
 public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
  config.exposeIdsFor(Location.class);
  config.exposeIdsFor(User.class);
 }
}