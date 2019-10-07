package uam.aleksy.lab1;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import uam.aleksy.lab1.scopes.PrototypeBean;
import uam.aleksy.lab1.scopes.ScopesShowcase;
import uam.aleksy.lab1.scopes.SingletonBean;

@SpringBootApplication
public class Lab1Application6 implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application6.class, args);

        ScopesShowcase showcase = context.getBean(ScopesShowcase.class);

        showcase.showcase();

        showcase.setSingletonBeanFromContext();
        showcase.setPrototypeBeanFromContext();

        showcase.showcase();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public SingletonBean singletonBean() {
        return new SingletonBean();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    @Bean
    public ScopesShowcase scopesShowcase(PrototypeBean prototypeBean,
                                         SingletonBean singletonBean) {
        return new ScopesShowcase(prototypeBean, singletonBean);
    }
}
