package com.book.BookProject.inquiryboard;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryBoardService {

    @Autowired
    private InquiryBoardRepository inquiryBoardRepository;
    @Autowired
    private InquiryBoardMapper inquiryBoardMapper;

    public List<InquiryBoardDTO> inquiryBoardList() // 문의게시판 리스트
    {
//        return inquiryBoardRepository.findAll();
        List<InquiryBoard> inquiryBoards = inquiryBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "qidx"));

        return inquiryBoardMapper.toDtoList(inquiryBoards);
    }

    public InquiryBoardDTO inquiryBoardView(Long qidx) // 문의게시판 상세보기
    {
        InquiryBoard inquiryBoard = inquiryBoardRepository.findById(qidx).get();

        return inquiryBoardMapper.toDto(inquiryBoard);
    }

    public void inquiryBoardWrite(InquiryBoardDTO inquiryBoardDTO) //문의게시판 글 작성
    {
        InquiryBoard inquiryBoard = inquiryBoardMapper.toEntity(inquiryBoardDTO);
        inquiryBoardRepository.save(inquiryBoard);
    }

    public void inquiryBoardEditor(InquiryBoardDTO inquiryBoardDTO) //문의게시판 글 수정
    {
        InquiryBoard inquiryBoard = inquiryBoardMapper.toEntity(inquiryBoardDTO);
        inquiryBoardRepository.save(inquiryBoard);
    }

    public void inquiryBoardDelete(Long qidx) // 문의게시판 글 삭제
    {
        inquiryBoardRepository.deleteById(qidx);
    }

    @Transactional
    public int inquiryBoardViewCount(Long qidx) // 문의게시판 조회수
    {
        return inquiryBoardRepository.viewCount(qidx);
    }



}
