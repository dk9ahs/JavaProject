package com.book.BookProject;

import com.book.BookProject.message.MessageDTO;
import com.book.BookProject.message.MessageService;
import com.book.BookProject.salesboard.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    MemberService memberService;

    // 쪽지 보낼 폼
    @GetMapping("/form")
    public String form(Model model, String receiver) {

        String sId = SecurityContextHolder.getContext().getAuthentication().getName();
        String nick = memberService.findNickById(sId);
        model.addAttribute("loginNick", nick);

        model.addAttribute("receiver", receiver);


        return "member/MessageForm";
    }

    @GetMapping("/view")
    public String view(Model model, Long msidx) {

        // 쪽지 상세보기
        String sId = SecurityContextHolder.getContext().getAuthentication().getName();
        String nick = memberService.findNickById(sId);
        model.addAttribute("loginNick", nick);
        
        model.addAttribute("messages", messageService.getMessageByIdx(msidx));

        return "member/MessageView";
    }

    @GetMapping("/sendview")
    public String sendview(Model model, Long msidx) {

        // 쪽지 상세보기
        String sId = SecurityContextHolder.getContext().getAuthentication().getName();
        String nick = memberService.findNickById(sId);
        model.addAttribute("loginNick", nick);

        model.addAttribute("messages", messageService.getMessageByIdx(msidx));

        return "member/MessageSendView";
    }

    // 받은 쪽지함
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") int page) {

        String sId = SecurityContextHolder.getContext().getAuthentication().getName();
        String nick = memberService.findNickById(sId);

        if (sId.equals("anonymousUser")) {
            model.addAttribute("loginNick", "Guest");
        } else {
            model.addAttribute("loginNick", nick);
        }

        Page<MessageDTO> listPage = messageService.messageList(nick, page-1);
        model.addAttribute("messageList", listPage.getContent());

        long totalCount = listPage.getTotalElements();
        int totalPage = listPage.getTotalPages();
        int currentGroup = (page - 1) / 5; // 현재 그룹 (0부터 시작)
        int pageSize = listPage.getSize();

        model.addAttribute("salesBoards", listPage.getContent());
        model.addAttribute("totalPage", totalPage); // 총 페이지
        model.addAttribute("currentPage", page); // 현재 페이지 추가
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", pageSize);

        Long count = messageService.countReadStatus(nick);
        model.addAttribute("count", count);
        System.out.println(count);

        return "member/MessageList";
    }

    @GetMapping("/sentlist")
    public String sentlist(Model model,
                       @RequestParam(defaultValue = "1") int page) {

        String sId = SecurityContextHolder.getContext().getAuthentication().getName();
        String nick = memberService.findNickById(sId);
        model.addAttribute("loginNick", nick);

        Page<MessageDTO> listPage = messageService.messagesentList(nick, page-1);
        model.addAttribute("messageList", listPage.getContent());

        long totalCount = listPage.getTotalElements();
        int totalPage = listPage.getTotalPages();
        int currentGroup = (page - 1) / 5; // 현재 그룹 (0부터 시작)
        int pageSize = listPage.getSize();

        model.addAttribute("salesBoards", listPage.getContent());
        model.addAttribute("totalPage", totalPage); // 총 페이지
        model.addAttribute("currentPage", page); // 현재 페이지 추가
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", pageSize);

        Long count = messageService.countReadStatus(nick);
        model.addAttribute("count", count);
        System.out.println(count);

        return "member/MessageSentList";
    }

    @GetMapping("/relist")
    public String relist(Model model,
                       @RequestParam(defaultValue = "1") int page) {

        String sId = SecurityContextHolder.getContext().getAuthentication().getName();
        String nick = memberService.findNickById(sId);

        if (sId.equals("anonymousUser")) {
            model.addAttribute("loginNick", "Guest");
        } else {
            model.addAttribute("loginNick", nick);
        }

        Page<MessageDTO> listPage = messageService.messageList(nick, page-1);
        model.addAttribute("messageList", listPage.getContent());

        long totalCount = listPage.getTotalElements();
        int totalPage = listPage.getTotalPages();
        int currentGroup = (page - 1) / 5; // 현재 그룹 (0부터 시작)
        int pageSize = listPage.getSize();

        model.addAttribute("salesBoards", listPage.getContent());
        model.addAttribute("totalPage", totalPage); // 총 페이지
        model.addAttribute("currentPage", page); // 현재 페이지 추가
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", pageSize);

        Long count = messageService.countReadStatus(nick);
        model.addAttribute("count", count);
        System.out.println(count);

        return "member/MessageList :: #messageListTable";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMessages(@RequestBody List<Long> msidxList){
        messageService.hideMessages(msidxList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/senddelete")
    public ResponseEntity<Void> senddeleteMessages(@RequestBody List<Long> msidxList){
        messageService.sendHideMessages(msidxList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
