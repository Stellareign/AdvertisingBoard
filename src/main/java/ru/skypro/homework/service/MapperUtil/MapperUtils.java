package ru.skypro.homework.service.MapperUtil;

import org.springframework.stereotype.Service;

@Service
public interface MapperUtils<T, F> {

    T mapToDto(F entity);

    F mapToEntity(T dto);
}
