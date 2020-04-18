package com.max.erickson.dynamic;

import org.junit.Test;

import static com.max.erickson.dynamic.BasicArithmeticExpression.findShortestExpression;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BasicArithmeticExpressionTest {

    @Test
    public void findShortestExpressionNormalCase() {
        assertThat(findShortestExpression(14)).isEqualTo("(1 + 1) * ((1) + ((1 + 1) * (1 + 1 + 1)))");
        assertThat(findShortestExpression(20)).isEqualTo("(1 + 1) * ((1) + ((1 + 1 + 1) * (1 + 1 + 1)))");
    }

    @Test
    public void findShortestExpressionForOne() {
        assertThat(findShortestExpression(1)).isEqualTo("1");
    }

    @Test
    public void findShortestExpressionForZeroShouldFail() {
        assertThatThrownBy(() -> findShortestExpression(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Negative value or zero passed: 0");
    }

    @Test
    public void findShortestExpressionForNegativeValueShouldFail() {
        assertThatThrownBy(() -> findShortestExpression(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Negative value or zero passed: -5");

        assertThatThrownBy(() -> findShortestExpression(Integer.MIN_VALUE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Negative value or zero passed: " + Integer.MIN_VALUE);
    }

}
