package dev.ubaid.ssbwj.service.mapper;

import org.mapstruct.Named;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

import java.util.List;


public interface EntityMapper<D, E> {
    E toEntity(D dto);
    D toDTO(E entity);
    
    List<E> toEntity(List<D> dtoList);
    List<D> toDTO(List<E> entityList);
    
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}
