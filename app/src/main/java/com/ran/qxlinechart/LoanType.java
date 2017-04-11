package com.ran.qxlinechart;

/**
 * Created by lenovo on 2017/4/1.
 */

public abstract class LoanType {
    public static class Type {
        public static final int TYPE_LOAN = 0;
        public static final int TYPE_CURRENT = 1;
    }

    public final Integer type;

    public LoanType(Integer type) {
        this.type = type;
    }
}
