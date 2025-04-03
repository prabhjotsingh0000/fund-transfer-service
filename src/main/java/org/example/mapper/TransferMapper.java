package org.example.mapper;

import org.example.dto.TransferRequestDTO;
import org.example.dto.TransferResponseDTO;
import org.example.dto.TransferSummaryDTO;
import org.example.model.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Transfer toEntity(TransferRequestDTO dto);

    TransferResponseDTO toResponseDTO(Transfer transfer);

    List<TransferResponseDTO> toResponseDTOList(List<Transfer> transfers);

    @Mapping(target = "id", source = "transfer.id")
    @Mapping(target = "amount", source = "transfer.amount")
    @Mapping(target = "status", source = "transfer.status")
    @Mapping(target = "createdAt", source = "transfer.createdAt")
    TransferSummaryDTO toSummaryDTO(Transfer transfer);
}
