package com.max.erickson.dynamic;

import org.junit.Test;

import static com.max.erickson.dynamic.LongestPalindromeSubsequence.findLongest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LongestPalindromeSubsequenceTest {

    @Test
    public void findLongestNormalCase() {
        assertThat(findLongest("MAHDYNAMICPROGRAMZLETMESHOWYOUTHEM")).isEqualTo(11);
    }

    @Test
    public void indLongestWithNullStringShouldFail() {
        assertThatThrownBy(() -> findLongest(null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("null 'str' argument detected");
    }
}
