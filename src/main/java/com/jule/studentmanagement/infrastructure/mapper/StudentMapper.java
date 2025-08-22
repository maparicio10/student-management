package com.jule.studentmanagement.infrastructure.mapper;

import com.jule.studentmanagement.infrastructure.entity.StudentEntity;
import com.jule.studentmanagement.infrastructure.dto.StudentDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "countryInfo", ignore = true)
    @Mapping(target = "externalPost", ignore = true)
    StudentDTO toDTO(StudentEntity student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "countryCode", ignore = true)
    @Mapping(target = "externalPostId", ignore = true)
    StudentEntity toEntity(StudentDTO studentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateStudentFromDTO(StudentDTO studentDTO, @MappingTarget StudentEntity student);
}
