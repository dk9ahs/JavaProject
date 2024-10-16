package com.book.BookProject.inquiryreply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryReplyService {

    @Autowired
    private InquiryReplyRepository inquiryReplyRepository;
    @Autowired
    private InquiryReplyMapper inquiryReplyMapper;
}
