package com.book.BookProject.tradeboard;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name="TRADEBOARD")
public class TradeBoard {

    @Id
    @GeneratedValue
    private Long tidx;
//    @ManyToOne
//    @JoinColumn(name = "member")
    private String nick;
    @Column(name="ttitle")
    private String title;
    @Column(name="tprice")
    private String price;
    @Column(name="tcontent")
    private String content;
    @Column(name="tclassification")
    private String classification;
    @Column(name="tregion")
    private String region;
    @Column(name="tcreate_date")
    private LocalDate createDate;
    @Column(name="tupdate_date")
    private LocalDate updateDate;
    @Column(name="tview_count")
    private int viewCount;
    @Column(name="tlike_count")
    private int likeCount;
    @Column(name="tofile")
    private String ofile;
    @Column(name="tsfile")
    private String sfile;
    @Column(name="tdown_count")
    private int downCount;
}
