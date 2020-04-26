package com.max.erickson.dynamic;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BalancedParenthesesTest {

    @Test
    public void longestBalancedNormalCases() {
        assertThat(BalancedParentheses.longestBalanced("(([][)])[](()")).isEqualTo(10);
        assertThat(BalancedParentheses.longestBalanced("([][)])[]")).isEqualTo(8);
        assertThat(BalancedParentheses.longestBalanced("[][]")).isEqualTo(4);
    }

    @Test
    public void checkInvalidStringThrowsException(){
        assertThatThrownBy(()-> BalancedParentheses.longestBalanced("[]A()")).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Invalid parenthesis string '[]A()' at index: 2, expected characters: ");
    }

}
