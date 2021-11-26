package org.soloactive.tick.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.soloactive.core.command.Command;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString
public class PostTickCommand extends Command {

    public String instrument;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public BigDecimal price;
    public long timestamp;

    public static PostTickCommand create(PostTickCommand command) {
        PostTickCommand cmd = new PostTickCommand();
        cmd.id = UUID.randomUUID();
        cmd.aggregateId = UUID.randomUUID();
        cmd.instrument = command.instrument;
        cmd.price = command.price;
        cmd.timestamp = command.timestamp;
        return cmd;
    }
}
