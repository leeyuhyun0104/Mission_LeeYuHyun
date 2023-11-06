package com.ll;

import java.util.Scanner;

public class App {
    public static void run() {
        System.out.println("== 명언 앱 ==");
        String cmd;

        while (true){
            System.out.print("명령) ");
            Scanner scanner = new Scanner(System.in);
            cmd = scanner.nextLine();      // 명령 입력 받기

            switch (cmd){
                case "종료":
                    return;   // 명령: "종료"일 때 앱 종료

                case "등록":
                    System.out.print("명언 : ");
                    String content = scanner.nextLine();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine();
            }
        }
    }
}
