ZTP homework Lab 1
Aleksy Wróblewski
s426285

## Rozgrzewka:
```
public class HelloWorld {
    public HelloWorld() {
        System.out.println("Hello world");
    }
}

public class GoodbyeWorld {
    public GoodbyeWorld() {
        System.out.println("Goodbye");
    }
}



@SpringBootApplication
public class Lab1Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab1Application.class, args);
	}

	@Bean
	public HelloWorld helloWorld() {
		return new HelloWorld();
	}

	@PreDestroy
	public GoodbyeWorld goodbyeWorld() {
		return new GoodbyeWorld();
	}
```

## DI:
### 4. Toolbox:
### 4.1, 4.2:
```
public interface Tool {}
public class Toolbox {}
```

### 4.3:
```
	@Bean
	public Toolbox toolbox(final Tool tool) {
		return new Toolbox();
	}
```
Wynik - błąd z następującym opisem:
```
Parameter 0 of method toolbox in uam.aleksy.lab1.Lab1Application required a bean of type 'uam.aleksy.lab1.tool.Tool' that could not be found.
```

### 4.4
```
public class ToolImpl implements Tool() {}

@Bean
public Tool toolFactory() {
	return new ToolImpl();
}
```

### 4.5
```
public class Screwdriver implements Tool {}
public class Hammer implements Tool {}

@Bean
public Tool hammer() {
	return new Hammer();
}

@Bean
public Tool screwdriver() {
	return new Screwdriver();
}

@Bean
public Toolbox toolbox(final Tool tool) {
	return new Toolbox();
}
```
Wynik - mamy dwa beany typu Tool i springowy kontener IoC nie wie który wybrać, kompilator zwraca błąd z następującym opisem:
```
Parameter 0 of method toolbox in uam.aleksy.lab1.Lab1Application required a single bean, but 2 were found:
	- hammer: defined by method 'hammer' in uam.aleksy.lab1.Lab1Application
	- screwdriver: defined by method 'screwdriver' in uam.aleksy.lab1.Lab1Application
```
### 4.6
Rozwiązanie z użyciem anotacji @Qualifier:
```
	@Bean
	public Toolbox toolbox(@Qualifier("hammer") final Tool tool) {
		return new Toolbox();
	}
```

### 4.7
Używamy anotacji @Autowired, która injectuje listę implementacji interfejsu:
```
	@Autowired
	public Toolbox toolbox(List<Tool> tools) {
		return new Toolbox();
	}
```

### 4.8
Rozwiązanie z użyciem @Order
```
@Bean
	@Order(2)
	public Tool hammer() {
		return new Hammer();
	}

	@Bean
	@Order(1)
	public Tool screwdriver() {
		return new Screwdriver();
	}
```

### 4.9
Przykładowe rozwiązanie wykorzystujące @Autowired Environment do odczytania profilu i System.setProperty do ustawienia go:
```
@SpringBootApplication
public class Lab1Application {
	@Autowired
	Environment environment;

	private static String BUILDER_PROFILE = "builder";
	private static String MECHANIC_PROFILE = "mechanic";

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", MECHANIC_PROFILE);
		SpringApplication.run(Lab1Application.class, args);
	}

	@Autowired
	public Toolbox toolbox(Tool tool) {
		return new Toolbox(tool);
	}

	@Bean
	public Tool toolFactory() {
		// dla prostoty zakładamy że jest jeden profil
		String profileName = environment.getActiveProfiles()[0];
		if (profileName.equals(BUILDER_PROFILE)) {
			return new Hammer();
		} else if (profileName.equals(MECHANIC_PROFILE)) {
			return new Screwdriver();
		}

		throw new IllegalArgumentException("Incorrect profile");
	}
}
```

### 4.10, 4.10.1
```
public interface Tool {
    default void hello() {
        System.out.println("I am a tool");
    }
}

@Bean
public Tool anonymous() {
	Tool tool = new Tool() {
		@Override
		public void hello() {
			System.out.println("I am an anonymous tool");
		}
	};
	tool.hello();
	return tool;
}
```

## 5. Ying-yang

###5.1
```
public class Yang {
    private Ying ying;

    public Yang() {}

    public Yang(Ying ying) {
        this.ying = ying;
    }

    public void setYing(Ying ying) {
        this.ying = ying;
    }
}

public class Ying {
    private Yang yang;

    public Ying() {}

    public void setYang(Yang yang) {
        this.yang = yang;
    }

    public Ying(Yang yang) {
        this.yang = yang;
    }
}

@SpringBootApplication
public class Lab1Application5 {
    public static void main(String[] args) {
        SpringApplication.run(Lab1Application5.class, args);
    }

    @Bean
    public Yang yang() {
        return new Yang();
    }

    @Bean
    public Ying ying() {
        return new Ying();
    }
}
```

### 5.2
### 5.2.1 - constructor injection
```
@Bean
public Yang yang(Ying ying) {
	return new Yang(ying);
}

@Bean
public Ying ying(Yang yang) {
	return new Ying(yang);
}
```

### 5.2.2 - setter injection
```
@SpringBootApplication
public class Lab1Application5 {
    private Yang yang;
    private Ying ying;

    @Resource
    protected void setYing(Ying ying) {
        this.ying = ying;
    }

    @Resource
    protected void setYang(Yang yang) {
        this.yang = yang;
    }


    public static void main(String[] args) {
        SpringApplication.run(Lab1Application5.class, args);
    }


    @Bean
    public Ying ying() {
        System.out.println("YANG IS NULL " + (yang == null));
        return new Ying(yang);
    }

    @Bean
    public Yang yang() {
        System.out.println("YING IS NULL " + (ying == null));
        return new Yang(ying);
    }
}
```


### 5.2.3 - field injection
```
@SpringBootApplication
public class Lab1Application5 {
    @Resource
    private Yang yang;

    @Resource
    private Ying ying;

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application5.class, args);
    }

    @Bean
    public Ying ying() {
        System.out.println("YANG IS NULL " + (yang == null));
        return new Ying(yang);
    }

    @Bean
    public Yang yang() {
        System.out.println("YING IS NULL " + (ying == null));
        return new Yang(ying);
    }
```
### 5.3
### (5.3.1)
Constructor injection - występuje tzw. cyclic dependency ponieważ bean Ying potrzebuje do zainicjalizowania beana Yang,
 a bean Yang potrzebuje beana Ying.  

```
The dependencies of some of the beans in the application context form a cycle:

┌─────┐
|  yang defined in uam.aleksy.lab1.Lab1Application5
↑     ↓
|  ying defined in uam.aleksy.lab1.Lab1Application5
└─────┘
```

### (5.3.2)
Setter injection - jedno z Ying lub Yang jest null, zależnie od tego, który setter jest wyżej w kodzie (np. jeśli pierwszy zostaje wywołany setYing to Yang jest null, dla logów z programu przykładowo:
```
YANG IS NULL true
YING IS NULL false
```

### (5.3.3)
Field injection - zachowanie analogiczne do setter injection, tj. jedno z pól jest null, zależnie od kolejności utworzenia

### 5.4
W przypadku różnych kombinacji (np. jeden setter injection, jeden constructor injection itp.) rezultat jest podobny i
nie rozwiązuje problemu circular dependency.


##6. Singletony/prototypy
### 6.1
Singleton to domyślny scope dla beanów w Springu, wystarczy zatem @Bean (lub inna anotacja do beanów) i NIE jest potrzebna dodatkowa anotacja,
jednak jeżeli chcemy explicite zdefiniować scope możemy użyć dekoratora @Scope:
```
public class SingletonBean() {}

@Bean
@Scope(BeanDefinition.SCOPE_SINGLETON)
public SingletonBean singletonBean() {
	return new SingletonBean();
}
```

### 6.2
```
public class PrototypeBean {}

@Bean
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public PrototypeBean prototypeBean() {
	return new PrototypeBean();
}
```

### 6.3
```
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
```

### 6.3.4
Po stworzeniu beana ScopeShowcase ze standardowo wstrzykniętymi beanami Prototype i Singleton (constructor injection):
```
PrototypeBean@7b64240d
SingletonBean@62fad19
```
Po wywołaniu setterów ustawiających pola z kontekstu:
```
PrototypeBean@47dbb1e2
SingletonBean@62fad19
```

Zgodnie z powyższym SingletonBean pozostał ten sam, zmianie natomiast uległ PrototypeBean - ten pobrany z kontekstu to inny obiekt niż
ten który został wstrzyknięty w konstruktorze. Wynika to z cech prototype scope - sprawia on, że przy każdym requeście po dany bean zostaje
tworzona nowa instancja.

 (source: https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch04s04.html)
