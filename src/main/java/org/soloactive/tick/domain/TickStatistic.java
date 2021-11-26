package org.soloactive.tick.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TickStatistic {

    private double avg;
    private double max;
    private double min;
    private int count;
}
