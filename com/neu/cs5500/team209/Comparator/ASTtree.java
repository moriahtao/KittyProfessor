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

    /**
     * Constructor for the class
     * @param data  object
     * @param parent parent ASTtree
     */
    public ASTtree(T data, ASTtree<T> parent) {
        this.data = data;
        this.children = new LinkedList<ASTtree<T>>();
        this.parent = parent;
    }

    /**
     * provides iterate over ASTtree, required as part of iterable class.
     * Will be implemented later
     * @return Iterator
     */
    @Override
    public Iterator<ASTtree<T>> iterator() {
        // to be implemented
        return null;
    }
}
