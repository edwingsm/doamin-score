package ie.newwhip.social.doamin.score;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoaminScoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DoaminScoreApplication.class, args);
	}

	@Autowired
	private DomainService domainService;

	@Override
	public void run(String... args) throws Exception {

		while(true){
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter Command: ");
			String command = scanner.next();
			System.out.println("Command "+command);
			DomainScore domainScore =domainService.save(command);
			System.out.println(domainScore);
			//https://picocli.info/man/3.x/quick-guide.html
		}

	}
}
