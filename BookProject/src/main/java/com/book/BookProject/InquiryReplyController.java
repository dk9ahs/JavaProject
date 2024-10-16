package com.book.BookProject;

import com.book.BookProject.inquiryboard.InquiryBoardService;
import com.book.BookProject.inquiryreply.InquiryReplyService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/inquiryboard")
@Controller
public class InquiryReplyController
{
    @Autowired
    InquiryReplyService inquiryReplyService;
    @Autowired
    ServletContext context;


}
