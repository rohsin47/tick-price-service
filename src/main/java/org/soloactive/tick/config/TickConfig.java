package org.soloactive.tick.config;

import org.soloactive.core.command.CommandResolver;
import org.soloactive.core.event.EventPublisher;
import org.soloactive.core.event.EventResolver;
import org.soloactive.core.event.store.EventStore;
import org.soloactive.core.event.store.InMemoryEventStore;
import org.soloactive.tick.command.DefaultCommandDispatcher;
import org.soloactive.tick.command.PostTickCommand;
import org.soloactive.tick.command.PostTickCommandHandler;
import org.soloactive.tick.event.DefaultEventPublisher;
import org.soloactive.tick.event.TickPostedEvent;
import org.soloactive.tick.event.TickPostedEventHandler;
import org.soloactive.tick.repository.TickRepository;
import org.soloactive.tick.service.TickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TickConfig {

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        CommandResolver commandResolver = applicationContext.getBean(CommandResolver.class);
        EventResolver eventResolver = applicationContext.getBean(EventResolver.class);
        EventStore eventStore = applicationContext.getBean(EventStore.class);
        TickRepository tickRepository = applicationContext.getBean(TickRepository.class);
        commandResolver.register(new PostTickCommandHandler(eventStore), PostTickCommand.class);
        eventResolver.register(new TickPostedEventHandler(tickRepository), TickPostedEvent.class);
    }

    @Bean
    public EventPublisher eventPublisher(EventResolver eventResolver){
        return new DefaultEventPublisher(eventResolver);
    }

    @Bean
    public EventStore eventStore(EventPublisher eventPublisher){
        return new InMemoryEventStore(eventPublisher);
    }

    @Bean
    public TickRepository readRepository(){
        return new TickRepository();
    }

    @Bean
    public CommandResolver commandResolver(){
        return CommandResolver.getInstance();
    }

    @Bean
    public DefaultCommandDispatcher defaultCommandDispatcher(CommandResolver commandResolver){
        return new DefaultCommandDispatcher(commandResolver);
    }

    @Bean
    public EventResolver eventResolver() {
        return new EventResolver();
    }

    @Bean
    public TickService tickService(DefaultCommandDispatcher defaultCommandDispatcher, TickRepository tickRepository){
        return new TickService(defaultCommandDispatcher, tickRepository);
    }
}
