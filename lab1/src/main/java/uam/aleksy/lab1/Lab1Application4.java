package uam.aleksy.lab1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import uam.aleksy.lab1.tool.Screwdriver;
import uam.aleksy.lab1.tool.Tool;
import uam.aleksy.lab1.tool.Hammer;
import uam.aleksy.lab1.tool.Toolbox;
import uam.aleksy.lab1.world.GoodbyeWorld;
import uam.aleksy.lab1.world.HelloWorld;

import javax.annotation.PreDestroy;

@SpringBootApplication
@Profile("builder")
public class Lab1Application4 {
	@Autowired
	Environment environment;

	private static String BUILDER_PROFILE = "builder";
	private static String MECHANIC_PROFILE = "mechanic";

	public static void main(String[] args) {
//		System.setProperty("spring.profiles.active", MECHANIC_PROFILE);
		SpringApplication.run(Lab1Application4.class, args);
	}

	@Autowired
	public Toolbox toolbox(@Qualifier("toolFactory") Tool tool) {
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

	@Bean
	public HelloWorld helloWorld() {
		return new HelloWorld();
	}

	@PreDestroy
	public GoodbyeWorld goodbyeWorld() {
		return new GoodbyeWorld();
	}
}

