package ru.skypro.homework.service.impl.MapperUtil;

public interface MapperUtils<T, F> {

    T mapToDto(F entity);

    F mapToEntity(T dto);
}
