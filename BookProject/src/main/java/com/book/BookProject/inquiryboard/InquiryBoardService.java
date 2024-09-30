package com.book.BookProject.inquiryboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryBoardService {

    @Autowired
    private InquiryBoardRepository inquiryBoardRepository;

    public List<InquiryBoard> selectAll()
    {
        return inquiryBoardRepository.findAll();
    }
}
