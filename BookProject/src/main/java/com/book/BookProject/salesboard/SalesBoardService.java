package com.book.BookProject.salesboard;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesBoardService {
    @Autowired
    private SalesBoardRepository salesBoardRepository;

    private final SalesBoardMapper salesBoardMapper;

    // 게시글 리스트 보기
    public List<SalesBoardDTO> getAllSalesBoards(){
        List<SalesBoard> salesBoards = salesBoardRepository.findSalesBoardPost();
        return salesBoardMapper.toDtoList(salesBoards);
    }

    // 게시글 작성하기
    public void createSalesBoard(SalesBoardDTO salesBoardDTO) {
        SalesBoard salesBoard = salesBoardMapper.toEntity(salesBoardDTO);

        salesBoardRepository.save(salesBoard);
    }

    // 게시글 상세 조회
    public SalesBoardDTO getSalesBoardById(Long sidx) {
        SalesBoard salesBoard = salesBoardRepository.findById(sidx).get();

        return salesBoardMapper.toDto(salesBoard);
    }

    // 게시글 삭제하기
    public void deletedSalesBored(Long sidx){
        salesBoardRepository.deleteById(sidx);
    }

    // 게시글 수정하기
    public void updateSalesBored(Long sidx, SalesBoardDTO salesBoardDTO){
        SalesBoard salesBoard = salesBoardRepository.findById(sidx).get();
        SalesBoardDTO originalDTO = salesBoardMapper.toDto(salesBoard);

        originalDTO.setSidx(sidx);
        originalDTO.setNick(salesBoardDTO.getNick());
        originalDTO.setClassification(salesBoardDTO.getClassification());
        originalDTO.setRegion(salesBoardDTO.getRegion());
        originalDTO.setTitle(salesBoardDTO.getTitle());
        originalDTO.setBooktitle(salesBoardDTO.getBooktitle());
        originalDTO.setAuthor(salesBoardDTO.getAuthor());
        originalDTO.setPublisher(salesBoardDTO.getPublisher());
        originalDTO.setPrice(salesBoardDTO.getPrice());
        originalDTO.setContent(salesBoardDTO.getContent());
        
        //  이미지 파일 변경되었을때만 업데이트 되도록
        if (salesBoardDTO.getOimage() != null && !salesBoardDTO.getOimage().isEmpty()) {
            originalDTO.setOimage(salesBoardDTO.getOimage());
        }

        if (salesBoardDTO.getSimage() != null && !salesBoardDTO.getSimage().isEmpty()) {
            originalDTO.setSimage(salesBoardDTO.getSimage());
        }

        salesBoardRepository.save(salesBoardMapper.toEntity(originalDTO));
    }

    // 게시판 목록 조회
//    public List<SalesBoardDTO> getAllSalesBoards() {
//        return salesBoardRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .toList();

//    }
//    public List<SalesBoardDTO> getAllSalesBoards() // 최신 게시물 보기
//    {
//        List<SalesBoard> salesBoardList= salesBoardRepository.findSalesBoardPost();
//        List<SalesBoardDTO> response = salesBoardList.stream()
//                .map(salesBoard -> modelMapper.map(salesBoard, SalesBoardDTO.class))
//                .collect(Collectors.toList());
//        return response;

//    }

    // 게시글 생성
//

//
//    // 게시글 삭제
//    public void deleteSalesBoard(Long sIdx, String currentNick) {
//        SalesBoard salesBoard = salesBoardRepository.findById(sIdx)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//
//        if (!salesBoard.getNick().equals(currentNick)) { // 유저 권한 확인
//            throw new IllegalArgumentException("삭제 권한이 없습니다.");
//        }
//        salesBoardRepository.deleteById(sIdx);
//    }
//
//    // 게시글 수정
//    public SalesBoard updateSalesBoard(Long sIdx, SalesBoardDTO salesBoardDTO) {
//        SalesBoard salesBoard = salesBoardRepository.findById(sIdx)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//
//        salesBoard.setSbooktitle(salesBoardDTO.getSbooktitle());
//        salesBoard.setScontent(salesBoardDTO.getScontent());
//        salesBoard.setPrice(salesBoardDTO.getPrice());
//        salesBoard.setOImage(salesBoardDTO.getOimage());
//        salesBoard.setSImage(salesBoardDTO.getSimage());
//        return salesBoardRepository.save(salesBoard);
//    }
//
//    // DTO -> Entity 변환 메서드
//    private SalesBoard convertToEntity(SalesBoardDTO dto) {
//        SalesBoard salesBoard = new SalesBoard();
//        salesBoard.setSbooktitle(dto.getSbooktitle());
//        salesBoard.setScontent(dto.getScontent());
//        salesBoard.setPrice(dto.getPrice());
//        salesBoard.setOImage(dto.getOimage());
//        salesBoard.setSImage(dto.getSimage());
//        return salesBoard;
//    }
//
//    // Entity -> DTO 변환 메서드
//    private SalesBoardDTO convertToDTO(SalesBoard entity) {
//        SalesBoardDTO dto = new SalesBoardDTO();
//        dto.setSidx(entity.getSidx());
//        dto.setNick(entity.getNick());
//        dto.setSbooktitle(entity.getSbooktitle());
//        dto.setScontent(entity.getScontent());
//        dto.setPrice(entity.getPrice());
//        dto.setOimage(entity.getOImage());
//        dto.setSimage(entity.getSImage());
//        dto.setScreateDate(entity.getScreatedate());
//        dto.setSupdateDate(entity.getSupdatedate());
//        return dto;
//    }
}