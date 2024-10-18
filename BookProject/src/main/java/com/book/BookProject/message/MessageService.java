package com.book.BookProject.message;

import com.book.BookProject.message.websocket.MessageMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class MessageService implements MessageServiceImpl{

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;


    // 보낸 쪽지 리스트
    public Page<MessageDTO> messageList(String receiver, int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "msidx"));
        Page<Message> messagePage = messageRepository.findByReceiverContaining(receiver, pageable);

        List<MessageDTO> dtomessage = messageMapper.toDtoList(messagePage.getContent());

        return new PageImpl<>(dtomessage, messagePage.getPageable(), messagePage.getTotalElements());
    }

    // 보낸 쪽지 리스트
    public Page<MessageDTO> messagesentList(String sender, int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "msidx"));
        Page<Message> messagePage = messageRepository.findBySenderContaining(sender, pageable);

        List<MessageDTO> dtomessage = messageMapper.toDtoList(messagePage.getContent());

        return new PageImpl<>(dtomessage, messagePage.getPageable(), messagePage.getTotalElements());
    }

    // 쪽지 상세 보기
    public MessageDTO getMessageByIdx(Long msidx){
        Message message = messageRepository.findById(msidx).get();

        return messageMapper.toDto(message);
    }

    // 안읽은 쪽지 카운트
    public Long countReadStatus(String receiver){
        return messageRepository.countByReceiverAndReadstatus(receiver, 0);
    }

    // 읽음 쪽지 표시
    public void updateReadStatus(Long msidx){
        messageRepository.updateReadstatus(msidx);
    }
}
