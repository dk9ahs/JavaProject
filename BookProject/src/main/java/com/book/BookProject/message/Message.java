package com.book.BookProject.message;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
//모든 필드 값을 파라미터로 받는 생성자를 생성
@AllArgsConstructor
//파라미터가 없는 디폴트 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name="MESSAGE")
@DynamicUpdate
public class Message {

    @Id // 엔티티의 주키(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msidx;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sender_nick", referencedColumnName = "NICK")
    @Column(name = "sender_nick")
    private String sender; // 보낸 사람

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "receiver_nick", referencedColumnName = "NICK")
    @Column(name = "receiver_nick")
    private String receiver; // 받는 사람

    @Column(name = "mstitle")
    private String title;

    @Column(name = "mscontent")
    private String content;

    @CreationTimestamp
    @Column(name = "mscreate_Date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "readstatus", columnDefinition = "INT default 0")
    private Integer readstatus;

    @PrePersist
    public void prePersist() {
        if (this.readstatus == null) {
            this.readstatus = 0; // 기본값 설정
        }
    }
}
