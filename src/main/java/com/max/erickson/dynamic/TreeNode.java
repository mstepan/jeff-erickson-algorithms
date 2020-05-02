package com.max.erickson.dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public final class TreeNode<T> {


    private final T value;

    private final List<TreeNode<T>> children;

    public static <U> TreeNode<U> of(U value) {
        return new TreeNode<>(value, new ArrayList<>());
    }

    private TreeNode(T value, List<TreeNode<T>> children) {
        this.value = value;
        this.children = children;
    }

    public T getValue() {
        return value;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    @SafeVarargs
    public final void addChildren(TreeNode<T>... childrenNodes) {
        checkArgument(childrenNodes != null, "null 'childrenNodes' parameter detected");
        children.addAll(Arrays.asList(childrenNodes));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
