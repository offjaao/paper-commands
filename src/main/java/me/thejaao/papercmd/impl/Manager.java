package me.thejaao.papercmd.impl;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
public abstract class Manager<T> {

    private List<T> elements = Lists.newArrayList();

    public Optional<T> get(Predicate<? super T> predicate) {
        return elements.stream().filter(predicate).findFirst();
    }
}