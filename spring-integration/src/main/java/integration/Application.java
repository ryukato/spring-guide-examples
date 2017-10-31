package integration;

import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@ImportResource("integration/integration.xml")
public class Application {
    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext context =
                new SpringApplication(Application.class)
                        .run(args);

        MessageChannel inChannel = context.getBean("numberChannel", MessageChannel.class);
        inChannel.send(new GenericMessage<String>("5"));


        context.close();
    }

}
