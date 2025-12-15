package com.weather.config;

import com.weather.util.SessionInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;

@Slf4j
@Configuration
@ComponentScan("com.weather")
@EnableWebMvc
@PropertySource("classpath:application.properties")

public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    private final SessionInterceptor sessionInterceptor;


    @Autowired
    public SpringConfig(ApplicationContext applicationContext, SessionInterceptor sessionInterceptor) {
        this.applicationContext = applicationContext;
        this.sessionInterceptor = sessionInterceptor;
    }

    @Bean

    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Bean
    public DataSource dataSource() {
       // DriverManagerDataSource dataSource = new DriverManagerDataSource();
        HikariDataSource  dataSource= new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
       // dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName("org.postgresql.Driver");

        dataSource.setMaximumPoolSize(20);
        dataSource.setMinimumIdle(5);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        return dataSource;
        //TODO поменять чтобы данные подставлялись из проперти
    }

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .validateOnMigrate(true)
                .outOfOrder(false)
                .cleanOnValidationError(false)
                .load();

        log.info("Запуск Flyway Migration...");
        try {
            flyway.migrate();
            log.info("Миграции успешно выполнены");
        } catch (Exception e) {
            log.error("Ошибка выполнения миграций: {}", e.getMessage());
            throw new RuntimeException("Ошибка миграции БД", e);
        }
        return flyway;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .excludePathPatterns(
                        "/sign-in",
                        "/sign-up",
                        "/sign-in-with-errors",
                        "/sign-up-with-errors",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/error",
                        "/",
                        "/favicon.ico"
                ).addPathPatterns("/**");
    }
}
