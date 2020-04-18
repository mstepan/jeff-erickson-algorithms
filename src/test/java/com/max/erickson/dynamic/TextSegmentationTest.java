package com.max.erickson.dynamic;

import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TextSegmentationTest {

    @Test
    public void countWays() {

        String text = "artistoil";

        Set<String> dictionary = Set.of("art", "is", "toil", "artist", "oil");

        assertThat(TextSegmentation.countWays(text, dictionary)).isEqualTo(2);

    }

}
