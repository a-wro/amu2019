package uam.aleksy.lab1.scopes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ScopesShowcase {
    @Autowired
    private ApplicationContext context;

    private PrototypeBean prototypeBean;
    private SingletonBean singletonBean;

    public ScopesShowcase(PrototypeBean prototypeBean, SingletonBean singletonBean) {
        this.prototypeBean = prototypeBean;
        this.singletonBean = singletonBean;
    }

    public void showcase() {
        System.out.println(prototypeBean);
        System.out.println(singletonBean);
    }

    public void setPrototypeBeanFromContext() {
        prototypeBean = context.getBean(PrototypeBean.class);
    }

    public void setSingletonBeanFromContext() {
        singletonBean = context.getBean(SingletonBean.class);
    }
}
