package io.iktech.demo;

import java.util.ArrayList;
import java.util.List;

public class Policies {

    private int first;
    private int next;
    private List<Policy> policies;

    public Policies() {
        this.policies = new ArrayList<>();
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
