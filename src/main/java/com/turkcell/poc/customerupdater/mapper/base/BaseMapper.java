package com.turkcell.poc.customerupdater.mapper.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<T extends Serializable, D extends Serializable> {

    T toEntity(D object);

    D toDTO(T object);

    default List<T> toEntityList(List<D> objectList){

        if (objectList == null || objectList.size() == 0)
            return new ArrayList<>();

        return objectList
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

    }

    default List<D> toDTOList(List<T> objectList){

        if (objectList == null || objectList.size() == 0)
            return new ArrayList<>();

        return objectList
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }

}
