package com.neu.cs5500.team209.Comparator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ASTtree for ast implementation.
 * Required methods will be implemented later.
 * Partly taken from
 * here https://stackoverflow.com/questions/3522454/java-tree-data-structure
 * @param <T> object type
 */

public class ASTtree<T> implements Iterable<ASTtree<T>> {
    T data;
    ASTtree<T> parent;
    List<ASTtree<T>> children;

    public ASTtree(T data, ASTtree<T> parent) {
        this.data = data;
        this.children = new LinkedList<ASTtree<T>>();
        this.parent = parent;
    }

    @Override
    public Iterator<ASTtree<T>> iterator() {
        // to be implemented
        return null;
    }
}
