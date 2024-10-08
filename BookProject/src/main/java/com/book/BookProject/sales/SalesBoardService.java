package com.book.BookProject.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesBoardService {

    private final SalesBoardRepository salesBoardRepository;

    // 게시판 목록 조회
    public List<SalesBoardDTO> getAllSalesBoards() {
        return salesBoardRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    // 게시글 생성
    public SalesBoard createSalesBoard(SalesBoardDTO salesBoardDTO) {
        // 더미 유저 정보 설정 (로그인 구현 전)
        String dummyNick = "user"; // 더미 유저 이름
        SalesBoard salesBoard = convertToEntity(salesBoardDTO);
        salesBoard.setNick(dummyNick);  // 더미 유저 설정

        System.out.println("SalesBoard 저장 전: " + salesBoard);
        return salesBoardRepository.save(salesBoard);
    }

    // 게시글 상세 조회
    public SalesBoardDTO getSalesBoardById(Long sIdx) {
        SalesBoard salesBoard = salesBoardRepository.findById(sIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return convertToDTO(salesBoard);
    }

    // 게시글 삭제
    public void deleteSalesBoard(Long sIdx, String currentNick) {
        SalesBoard salesBoard = salesBoardRepository.findById(sIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (!salesBoard.getNick().equals(currentNick)) { // 유저 권한 확인
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        salesBoardRepository.deleteById(sIdx);
    }

    // 게시글 수정
    public SalesBoard updateSalesBoard(Long sIdx, SalesBoardDTO salesBoardDTO) {
        SalesBoard salesBoard = salesBoardRepository.findById(sIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        salesBoard.setSbooktitle(salesBoardDTO.getSbooktitle());
        salesBoard.setScontent(salesBoardDTO.getScontent());
        salesBoard.setPrice(salesBoardDTO.getPrice());
        salesBoard.setOImage(salesBoardDTO.getOimage());
        salesBoard.setSImage(salesBoardDTO.getSimage());
        return salesBoardRepository.save(salesBoard);
    }

    // DTO -> Entity 변환 메서드
    private SalesBoard convertToEntity(SalesBoardDTO dto) {
        SalesBoard salesBoard = new SalesBoard();
        salesBoard.setSbooktitle(dto.getSbooktitle());
        salesBoard.setScontent(dto.getScontent());
        salesBoard.setPrice(dto.getPrice());
        salesBoard.setOImage(dto.getOimage());
        salesBoard.setSImage(dto.getSimage());
        return salesBoard;
    }

    // Entity -> DTO 변환 메서드
    private SalesBoardDTO convertToDTO(SalesBoard entity) {
        SalesBoardDTO dto = new SalesBoardDTO();
        dto.setSidx(entity.getSidx());
        dto.setNick(entity.getNick());
        dto.setSbooktitle(entity.getSbooktitle());
        dto.setScontent(entity.getScontent());
        dto.setPrice(entity.getPrice());
        dto.setOimage(entity.getOImage());
        dto.setSimage(entity.getSImage());
        dto.setScreateDate(entity.getScreatedate());
        dto.setSupdateDate(entity.getSupdatedate());
        return dto;
    }
}