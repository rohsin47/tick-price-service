package org.soloactive.tick.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.soloactive.tick.TickController;
import org.soloactive.tick.command.PostTickCommand;
import org.soloactive.tick.domain.TickStatistic;
import org.soloactive.tick.service.TickService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@RunWith(MockitoJUnitRunner.class)
public class TickControllerTest {

    @InjectMocks
    TickController tickController;

    @Mock
    TickService tickService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetStatistics() throws Exception {
        when(tickService.calculateTickStats()).thenReturn(TickStatistic.builder().build());
        ResponseEntity<TickStatistic> result = tickController.getStatistics();
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(tickService).calculateTickStats();
    }

    @Test
    public void testGetStatisticsByInstrument() throws Exception {
        when(tickService.calculateTickStatsByInstrument(anyString())).thenReturn(TickStatistic.builder().build());
        ResponseEntity<TickStatistic> result = tickController.getStatisticsByInstrument("test");
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(tickService).calculateTickStatsByInstrument(anyString());
    }

    @Test
    public void testPostCommand() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        PostTickCommand command = new PostTickCommand();
        ResponseEntity responseEntity = tickController.postTicks(command);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }



}
