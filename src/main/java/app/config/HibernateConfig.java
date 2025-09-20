package app.config;

import app.entities.*;
import app.utils.Utils;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateConfig {
    private static EntityManagerFactory emf;
    private static EntityManagerFactory emfTest;
    private static Boolean isTest = false;

    public static void setTest(Boolean test) {
        isTest = test;
    }

    public static Boolean getTest() {
        return isTest;
    }

    public static void setEmfTest(EntityManagerFactory emf){ emfTest = null;}

    public static EntityManagerFactory getEntityManagerFactory(String DBName) {
        if (emf == null)
            emf = createEMF(getTest(), DBName);
        return emf;
    }

    public static EntityManagerFactory getEntityManagerFactoryForTest(String testDBName) {
        if (emfTest == null){
            setTest(true);
            emfTest = createEMF(getTest(), testDBName);
        }
        return emfTest;
    }

    // TODO: IMPORTANT: Add Entity classes here for them to be registered with Hibernate
    private static void getAnnotationConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(Director.class);
        configuration.addAnnotatedClass(Genre.class);
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(MovieActorRelations.class);
        configuration.addAnnotatedClass(MovieDirectorRelations.class);

    }

    private static EntityManagerFactory createEMF(boolean forTest, String DBName) {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();

            setBaseProperties(props);
            if (forTest) {
                props = setTestProperties(props);
            } else if (System.getenv("DEPLOYED") != null) {
                setDeployedProperties(props, DBName);
            } else {
                props = setDevProperties(props, DBName);
            }
            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            EntityManagerFactory emf = sf.unwrap(EntityManagerFactory.class);
            return emf;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Properties setBaseProperties(Properties props) {
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.use_sql_comments", "false");
        return props;
    }

    private static Properties setDeployedProperties(Properties props, String DBName) {
        props.setProperty("hibernate.connection.url", System.getenv("CONNECTION_STR") + DBName);
        props.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
        return props;
    }

    private static Properties setDevProperties(Properties props, String DBName) {
        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + DBName);
        String DB_USERNAME = Utils.getPropertyValue("DB_USERNAME", "config.properties");
        String DB_PASSWORD = Utils.getPropertyValue("DB_PASSWORD", "config.properties");
        props.put("hibernate.connection.username", DB_USERNAME);
        props.put("hibernate.connection.password", DB_PASSWORD);
        return props;
    }

    private static Properties setTestProperties(Properties props) {
        props.put("hibernate.connection.driver_class", "org.h2.Driver");
        props.put("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        String TESTDB_USERNAME = Utils.getPropertyValue("TESTDB_USERNAME", "config.properties");
        String TESTDB_PASSWORD = Utils.getPropertyValue("TESTDB_PASSWORD", "config.properties");
        props.put("hibernate.connection.username", TESTDB_USERNAME);
        props.put("hibernate.connection.password", TESTDB_PASSWORD);
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        return props;
    }
}