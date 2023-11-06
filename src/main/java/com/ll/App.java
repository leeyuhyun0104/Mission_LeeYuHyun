package com.ll;

import java.util.Scanner;

public class App {
    public static void run() {
        System.out.println("== 명언 앱 ==");
        String cmd;
        int cnt = 0;

        while (true){
            System.out.print("명령) ");
            Scanner scanner = new Scanner(System.in);
            cmd = scanner.nextLine();      // 명령 입력 받기

            switch (cmd){
                case "종료":
                    return;   // 명령: "종료"일 때 앱 종료

                case "등록":
                    cnt=++cnt;

                    System.out.print("명언 : ");
                    String content = scanner.nextLine();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine();

                    System.out.printf("%d번 명언이 등록되었습니다.\n", cnt);
            }
        }
    }
}
