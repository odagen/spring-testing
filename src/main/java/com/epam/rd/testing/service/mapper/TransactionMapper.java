package com.epam.rd.testing.service.mapper;

import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface TransactionMapper {
    @Mappings({
            @Mapping(target = "id", expression = "java( transaction.getId().toString() )")
    })
    TransactionDTO toDTO(Transaction transaction);

    @Mappings({
            @Mapping(target = "id", expression = "java( UUID.randomUUID() )")
    })
    Transaction toDomain(TransactionDTO transaction);
}
