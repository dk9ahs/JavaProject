package com.book.BookProject.inquiryreply;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InquiryReplyMapper {

    InquiryReplyDTO toDto(InquiryReply inquiryReply);

    InquiryReply toEntity(InquiryReplyDTO inquiryReplyDTO);

    List<InquiryReplyDTO> toDtoList(List<InquiryReply> inquiryReplys);

    List<InquiryReply> toEntityList(List<InquiryReplyDTO> inquiryReplyDTOS);
}
