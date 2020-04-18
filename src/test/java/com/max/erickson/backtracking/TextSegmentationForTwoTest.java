package com.max.erickson.backtracking;

import org.junit.Test;

import java.util.Set;

import static com.max.erickson.backtracking.TextSegmentationForTwo.canBePartition;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TextSegmentationForTwoTest {

    @Test
    public void normalCase() {
        boolean actualRes = canBePartition("BOTHEARTHANDSATURNSPIN", "PINSTARTRAPSANDRAGSLAP",
                                           Set.of("BOT", "HEART", "HAND", "SAT", "URNS",
                                                  "PIN", "START", "RAPS", "AND", "RAGS", "LAP"));
        assertThat(actualRes).isTrue();
    }
}
