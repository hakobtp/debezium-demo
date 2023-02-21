package com.debezium.demo.common.mapper;

public interface FullMapper<E, D> extends Mapper<E, D>, ListMapper<E, D> {
}
