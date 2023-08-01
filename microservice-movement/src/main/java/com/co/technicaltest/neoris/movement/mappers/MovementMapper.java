package com.co.technicaltest.neoris.movement.mappers;

import com.co.technicaltest.neoris.movement.models.dto.MovementResponseDTO;
import com.co.technicaltest.neoris.movement.models.entity.documents.Movement;
import domain.models.enums.MovementType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MovementMapper {


    @Mapping(source = "accountType", target = "accountType", qualifiedByName = "getStringAccountType")
    MovementResponseDTO movementToMovementResponseDto(Movement movement);




    @Named("getStringAccountType")
    public static String getStringAccountType(MovementType movementType) {
        return movementType.getType();
    }
}
