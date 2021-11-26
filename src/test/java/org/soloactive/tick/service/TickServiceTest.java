package org.soloactive.tick.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.soloactive.tick.command.DefaultCommandDispatcher;
import org.soloactive.tick.command.PostTickCommand;
import org.soloactive.tick.domain.TickStatistic;
import org.soloactive.tick.repository.TickRepository;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class TickServiceTest {

    @Mock
    DefaultCommandDispatcher defaultCommandDispatcher;

    @Mock
    TickRepository tickRepository;

    @InjectMocks
    TickService tickService;

    @Test
    public void testCalculateTickerStatistics() {
        when(tickRepository.getEventsList()).thenReturn(Collections.emptyMap());
        TickStatistic data = tickService.calculateTickStats();
        assertNotNull(data);
        verify(tickRepository).getEventsList();
    }

    @Test
    public void testCalculateTickerStatisticsByInstrument() {
        when(tickRepository.getEventsList()).thenReturn(Collections.emptyMap());
        TickStatistic data = tickService.calculateTickStatsByInstrument("test");
        assertNotNull(data);
        verify(tickRepository).getEventsList();
    }

}
