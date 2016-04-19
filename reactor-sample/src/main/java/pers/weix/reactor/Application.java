package pers.weix.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

import static reactor.bus.selector.Selectors.$;

/**
 * @author weixing
 */
@SpringBootApplication
@EnableScheduling
@Component
public class Application implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Service
    public static class Receiver implements Consumer<Event<Integer>> {
        public void accept(Event<Integer> event) {
            logger.info("{}", event.getData());
        }
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Autowired
    private EventBus eventBus;

    @Autowired
    private Receiver receiver;

    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("test");
        eventBus.on($("test"), receiver);
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void test() {
        logger.info("test");
        eventBus.notify("test", new Event<Integer>(1));
    }

    public static void main(String[] args) {
        logger.info("test");
        SpringApplication.run(Application.class, args);
    }
}
