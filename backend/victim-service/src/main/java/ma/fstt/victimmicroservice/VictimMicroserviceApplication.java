package ma.fstt.victimmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication

@EnableDiscoveryClient
public class VictimMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VictimMicroserviceApplication.class, args);
	}

}
