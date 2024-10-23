package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class MapController
{
    // 컨트롤러 초기화 시 Streamlit 서버 실행
    @PostConstruct
    public void startStreamlit() {
        try {
            // Streamlit 서버를 백그라운드에서 실행
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python", "src/main/python/map.py", "streamlit", "run"
            );

            processBuilder.redirectErrorStream(true); // 오류와 출력을 동일한 스트림으로 연결
            processBuilder.start(); // 프로세스 시작

            System.out.println("Streamlit 서버가 성공적으로 시작되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Streamlit 서버 실행에 실패했습니다.");
        }
    }

    @RequestMapping("/librarymap")
    public String libraryMap()
    {
        return "guest/libraryMap";
    }
}
