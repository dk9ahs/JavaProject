package com.book.BookProject.inquiryboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "INQUIRYBOARD")
public class InquiryBoard {

    @Id
    @GeneratedValue
    private  Long qidx; // 인덱스
    private int qparentidx; //
    private String qparentid;
    private int qresponses;
    private String nick;
    private String qtitle;
    private String qcontent;
    private LocalDate qcreate_date;
    private LocalDate qupdate_date;
    private int qview_count;
    private int qlike_count;
    private String qofile;
    private String qsfile;
    private int qdown_count;

}
