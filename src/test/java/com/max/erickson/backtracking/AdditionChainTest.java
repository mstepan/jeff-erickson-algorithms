package com.max.erickson.backtracking;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AdditionChainTest {

    @Test
    public void findChain() {
        assertThat(AdditionChain.findChain(1)).containsExactly(1);
        assertThat(AdditionChain.findChain(2)).containsExactly(1, 2);
        assertThat(AdditionChain.findChain(3)).containsExactly(1, 2, 3);
        assertThat(AdditionChain.findChain(4)).containsExactly(1, 2, 4);
        assertThat(AdditionChain.findChain(5)).containsExactly(1, 2, 4, 5);
        assertThat(AdditionChain.findChain(7)).containsExactly(1, 2, 4, 6, 7);
        assertThat(AdditionChain.findChain(10)).containsExactly(1, 2, 4, 8, 10);
        assertThat(AdditionChain.findChain(11)).containsExactly(1, 2, 4, 8, 10, 11);

        // 1, 2, 3, 5, 10, 20, 23, 46, 92, 184, 187, 374
        // 1, 2, 4, 6, 10, 20, 40, 46, 92, 184, 368, 374
//        assertThat(AdditionChain.findChain(374)).containsExactly(1, 2, 3, 5, 10, 20, 23, 46, 92, 184, 187, 374);
    }
}
