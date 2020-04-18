package com.max.erickson.dynamic;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LongLiveBooleTest {

    @Test
    public void findTrueExpressionForExistingSolution() {
        assertThat(LongLiveBoole.findTrueExpression("T*F^T")).
                isNotEmpty().
                hasValue("((T * F) ^ T)");

        assertThat(LongLiveBoole.findTrueExpression("T*T+F^F*F+T^F")).
                isNotEmpty().
                hasValue("(((((T * T) + F) ^ (F * F)) + T) ^ F)");
    }

    @Test
    public void findTrueExpressionForNotExistingCase() {
        assertThat(LongLiveBoole.findTrueExpression("F*T^F")).
                isEmpty();
    }

    @Test
    public void canBeTrue() {
        // ((T * F) ^ T) = true
        assertThat(LongLiveBoole.canBeTrue("T*F^T")).isTrue();

        // (((((T * T) + F) ^ (F * F)) + T) ^ F) = true
        assertThat(LongLiveBoole.canBeTrue("T*T+F^F*F+T^F")).isTrue();
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
                hasMessage("Bad expression, value at index 3 should be a valid operator [*, +, ^], but found '-'");
    }

}
