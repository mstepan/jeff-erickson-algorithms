package com.max.erickson.dynamic;

import org.junit.Test;

import static com.max.erickson.dynamic.LongestForwardBackwardSubstring.findLength;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LongestForwardBackwardSubstringTest {

    @Test
    public void longestSingleCharAtTheEnd() {
        assertThat(findLength("SOMEVALUE")).isEqualTo(1);
    }

    @Test
    public void longestNoChars() {
        assertThat(findLength("SOME")).isEqualTo(0);
    }

    @Test
    public void longestNoOverlapping() {
        assertThat(findLength("REDIVIDE")).isEqualTo(3);
        assertThat(findLength("REDIVIDe")).isEqualTo(2);
    }

    @Test
    public void longestWithOverlapping() {
        assertThat(findLength("DYNAMICPROGRAMMINGMANYTIMES")).isEqualTo(4);
    }

    @Test
    public void longestSingleChar() {
        assertThat(findLength("RECURSION")).isEqualTo(1);
        assertThat(findLength("RECUrSION")).isEqualTo(0);
    }

    @Test
    public void longestTwoCharsString() {
        assertThat(findLength("AA")).isEqualTo(1);
    }

    @Test
    public void longestForOneCharString() {
        assertThat(findLength("A")).isEqualTo(0);
    }

    @Test
    public void longestForEmptyString() {
        assertThat(findLength("")).isEqualTo(0);
    }

    @Test
    public void longestNoForwardAndBackwardSubstringAtAll() {
        assertThat(findLength("ALGORITHM")).isEqualTo(0);
    }

}
