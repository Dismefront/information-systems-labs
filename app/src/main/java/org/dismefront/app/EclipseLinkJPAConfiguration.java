package org.dismefront.app;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.eclipse.persistence.config.BatchWriting;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class EclipseLinkJPAConfiguration extends JpaBaseConfiguration {
  protected EclipseLinkJPAConfiguration(
      DataSource dataSource,
      JpaProperties properties,
      ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
    super(dataSource, properties, jtaTransactionManager);
  }

  @Override
  protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
    return new EclipseLinkJpaVendorAdapter();
  }

  @Override
  protected Map<String, Object> getVendorProperties() {
    final Map<String, Object> ret = new HashMap<>();
    ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
    return ret;
  }

  @Bean("entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactory(
      EntityManagerFactoryBuilder builder, DataSource dataSource) {

    return builder
        .dataSource(dataSource)
        .packages("org.dismefront")
        .persistenceUnit("YourPersistenceUnitName")
        .properties(initJpaProperties())
        .build();
  }

  @Autowired private Environment env;

  @Bean
  public static DataSource dataSource(Environment env) {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("DB_DRIVER"));
    dataSource.setUrl(env.getProperty("DB_URL"));
    dataSource.setUsername(env.getProperty("DB_USER"));
    dataSource.setPassword(env.getProperty("DB_PASSWORD"));
    return dataSource;
  }

  @Bean
  public static PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  @Primary
  public static JpaProperties properties() {
    final JpaProperties jpaProperties = new JpaProperties();
    jpaProperties.setShowSql(true);
    jpaProperties.setDatabasePlatform(
        "org.eclipse.persistence.platform.database.PostgreSQLPlatform");
    return jpaProperties;
  }

  private static Map<String, ?> initJpaProperties() {
    final Map<String, Object> ret = new HashMap<>();
    // Add any JpaProperty you are interested in and is supported by your Database and JPA
    // implementation
    ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
    ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.FINEST_LABEL);
    ret.put(PersistenceUnitProperties.WEAVING, "false");
    ret.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
    ret.put(
        PersistenceUnitProperties.DDL_GENERATION_MODE,
        PersistenceUnitProperties.DDL_DATABASE_GENERATION);
    return ret;
  }
}
