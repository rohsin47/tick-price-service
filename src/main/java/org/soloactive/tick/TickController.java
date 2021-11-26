package org.soloactive.tick;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.tick.command.PostTickCommand;
import org.soloactive.tick.domain.TickStatistic;
import org.soloactive.tick.repository.TickRepository;
import org.soloactive.tick.service.TickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/price")
public class TickController {

    @Autowired
    TickService tickService;

    @Autowired
    TickRepository tickRepository;

    @GetMapping("/")
    public String getInfo() {
        return "Tick Price Service";
    }

    @PostMapping(path="/ticks", consumes = "application/json", produces = "application/json")
    public ResponseEntity postTicks(@RequestBody PostTickCommand postTickCommand) {
        log.info("Received {}", postTickCommand);
        tickService.post(postTickCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @GetMapping("/statistics")
    public ResponseEntity<TickStatistic> getStatistics() {
        return ResponseEntity.ok(tickService.calculateTickStats());
    }

    @GetMapping("/statistics/{instrument_identifier}")
    public ResponseEntity<TickStatistic> getStatisticsByInstrument(@PathVariable String instrument_identifier) {
        return ResponseEntity.ok(tickService.calculateTickStatsByInstrument(instrument_identifier));
    }

}
