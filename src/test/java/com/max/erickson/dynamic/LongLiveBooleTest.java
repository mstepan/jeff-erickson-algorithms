package com.max.erickson.dynamic;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LongLiveBooleTest {

    @Test
    public void canBeTrue() {
        assertThat(LongLiveBoole.canBeTrue("T*F^T")).isTrue();
    }

    @Test
    public void cannotBeTrue() {
        assertThat(LongLiveBoole.canBeTrue("F*T^F")).isFalse();
    }

    @Test
    public void invalidExpressionShouldFail() {
        assertThatThrownBy(() -> LongLiveBoole.canBeTrue("T*A+F^T")).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("Bad expression, value at index 2 should be 'T' or 'F', but found 'A'");

        assertThatThrownBy(() -> LongLiveBoole.canBeTrue("T*T-F^T")).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("Bad expression, value at index 3 should be a valid operator [+, *, ^], but found '-'");
    }

}
