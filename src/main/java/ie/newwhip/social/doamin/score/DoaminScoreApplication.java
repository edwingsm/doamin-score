package ie.newwhip.social.doamin.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.URL;

@SpringBootApplication
@EnableMongoRepositories
public class DoaminScoreApplication implements CommandLineRunner {



    public static void main(String[] args) throws Exception{
      //  File file = ResourceUtils.getFile("classpath:config/sample.txt");
      //  String content = new String(Files.readAllBytes(file.toPath()));
//        final EmbeddedElastic embeddedElastic = EmbeddedElastic.builder()
//                .withElasticVersion("5.0.0")
//                .withSetting(PopularProperties.TRANSPORT_TCP_PORT, 9350)
//                .withSetting(PopularProperties.CLUSTER_NAME, "app_cluster")
//               // .withIndex("social_score", IndexSettings.builder().withType("socialscore", content).build())
//                .build()
//                .start();
//        embeddedElastic.start();
        SpringApplication.run(DoaminScoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("App ready to serve client");
//        while (true) {
//            Runnable task2 = () -> {
//                System.out.println("Task #2 is running");
//            };
//            // start the thread
//            new Thread(task2).start();
//        }
//		while(true){
//			Scanner scanner = new Scanner(System.in);
//			System.out.print("Enter Command: ");
//			String command = scanner.next();
//			System.out.println("Command "+command);
//			SocialScoreDBO domainScore =domainService.save(command);
//			System.out.println(domainScore);
//			//https://picocli.info/man/3.x/quick-guide.html
//		}

        String add = "ADD https://www.google.com/search?query=soemthing 30";

        String[] params = add.split(" ");


        switch (params[0]){
            case "ADD":
                System.out.println("ADD");
                URL myUrl = new URL(params[1]);
                System.out.println(myUrl.getHost());
                System.out.println(myUrl.toString());
                System.out.println(Integer.parseInt(params[2]));
            case "REMOVE":
                System.out.println("REMOVE");
            case "EXPORT":
                System.out.println("EXPORT");
        }
    }
}
