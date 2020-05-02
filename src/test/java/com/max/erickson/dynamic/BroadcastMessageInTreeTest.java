package com.max.erickson.dynamic;

import org.junit.Test;

import static com.max.erickson.dynamic.BroadcastMessageInTree.minTripsCount;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BroadcastMessageInTreeTest {

    @Test
    public void minTripsCountNormalCase() {

        TreeNode<String> a = TreeNode.of("A");
        TreeNode<String> e = TreeNode.of("E");
        TreeNode<String> f = TreeNode.of("F");
        TreeNode<String> g = TreeNode.of("G");
        TreeNode<String> h = TreeNode.of("H");
        f.addChildren(g, h);
        a.addChildren(e, f);

        TreeNode<String> b = TreeNode.of("B");
        TreeNode<String> k = TreeNode.of("K");
        TreeNode<String> m = TreeNode.of("M");
        k.addChildren(m);
        b.addChildren(k);

        TreeNode<String> c = TreeNode.of("C");
        TreeNode<String> n = TreeNode.of("N");
        TreeNode<String> o = TreeNode.of("O");
        TreeNode<String> p = TreeNode.of("P");
        n.addChildren(o, p);
        c.addChildren(n);

        TreeNode<String> d = TreeNode.of("D");
        TreeNode<String> q = TreeNode.of("Q");
        TreeNode<String> r = TreeNode.of("R");
        TreeNode<String> s = TreeNode.of("S");
        TreeNode<String> t = TreeNode.of("T");
        TreeNode<String> u = TreeNode.of("U");
        TreeNode<String> v = TreeNode.of("V");
        q.addChildren(s, t);
        r.addChildren(u, v);
        d.addChildren(q, r);

        TreeNode<String> rootNode = TreeNode.of("W");
        rootNode.addChildren(a, b, c, d);

        assertThat(minTripsCount(rootNode)).isEqualTo(6);
    }

    @Test
    public void minTripsCountShouldFailIfNullTree() {
        //noinspection ResultOfMethodCallIgnored
        assertThatThrownBy(() -> minTripsCount(null)).
                isInstanceOf(IllegalArgumentException.class).hasMessage("null 'tree' parameter detected");

    }
}
