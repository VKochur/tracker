package team.mediasoft.education.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.repository.TypeRepository;

import java.util.List;
import java.util.UUID;

@EnableSwagger2
@SpringBootApplication
public class TrackerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(TrackerApplication.class, args);
		//test(applicationContext);
	}

	private static void test(ConfigurableApplicationContext applicationContext) {
		TypeRepository bean = applicationContext.getBean(TypeRepository.class);
		List<Type> allTypes = bean.findAll();
		System.out.println("-----");
		System.out.println(allTypes.size());

		for (int i = 0; i < 13; i++) {
			Type type = new Type();
			type.setName(UUID.randomUUID().toString()+ "_" + i);
			if (i==8) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			bean.save(type);
		}
	}

}
