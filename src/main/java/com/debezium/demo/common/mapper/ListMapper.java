package com.debezium.demo.common.mapper;

import java.util.List;

public interface ListMapper<E, D> {

    List<D> toDtoList(List<E> e);

    List<E> toEntityList(List<D> d);
}

