package com.xzit.common.sys.function;

@FunctionalInterface
public interface Polymerization<T,R,S> {
    S apply(T t,R r);
}
