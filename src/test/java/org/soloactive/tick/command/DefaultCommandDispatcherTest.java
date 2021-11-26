package org.soloactive.tick.command;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.soloactive.core.command.Command;
import org.soloactive.core.command.CommandHandler;
import org.soloactive.core.command.CommandResolver;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCommandDispatcherTest {

    private Boolean handlerCalled;

    public class MyCommand extends Command {
        private static final long serialVersionUID = 7729006766074319990L;

        public MyCommand() {
        }
    }

    public class MyCommandHandler implements CommandHandler {
        @Override
        public void handle(Command command) {
            handlerCalled = true;
        }

    }

    @Test
    public void findHandlerForMyCommand() {
        CommandResolver resolver = CommandResolver.getInstance();
        resolver.register(new MyCommandHandler(), MyCommand.class);

        CommandHandler handler = resolver.findHandlerFor(MyCommand.class);
        assertNotNull(handler);
    }

    @Test
    public void dispatchMyCommand() {
        handlerCalled = false;
        CommandResolver resolver = CommandResolver.getInstance();
        resolver.register(new MyCommandHandler(), MyCommand.class);

        DefaultCommandDispatcher dispatcher = new DefaultCommandDispatcher(resolver);
        dispatcher.dispatch(new MyCommand());
        assertTrue(handlerCalled);
    }
}
