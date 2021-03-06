package com.example.lilactests.config;

import java.util.HashSet;
import java.util.Set;

/**
 * For Choice Model ,save selected ItemId and whether in Choice Mode.
 */
public class MultiSelector {
    private Set<Long> mSet;
    private boolean isSelectable;

    public MultiSelector() {
        this.isSelectable = false;
        this.mSet = new HashSet<>();
    }

    public void add(Long item) {
        mSet.add(item);
    }

    public void remove(Long item) {
        mSet.remove(item);
    }

    public boolean contains(Long item) {
        return mSet.contains(item);
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        isSelectable = selectable;
    }

    public void clear() {
        mSet.clear();
    }

    public boolean isEmpty() {
        return mSet.isEmpty();
    }

    public int size() {
        return mSet.size();
    }
}

