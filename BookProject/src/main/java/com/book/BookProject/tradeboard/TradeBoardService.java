package com.book.BookProject.tradeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TradeBoardService {
    @Autowired
    private TradeBoardRepository tradeboardRepository;

    public List<TradeBoard> selectBoardPost() // 최신 게시물 보기
    {
        return tradeboardRepository.findTradeBoardPost();
    }

    public  TradeBoard postBoard(TradeBoard tradeBoard)
    {
        TradeBoard postBoard = tradeboardRepository.save(tradeBoard);
        return postBoard;
    }
}
