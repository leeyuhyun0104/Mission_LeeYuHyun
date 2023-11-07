package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    static String cmd;
    static int cnt = 0;    // 등록된 명언의 개수, 0부터 시작
    static List<Quotation> qList = new ArrayList<>();   // 명언 저장 리스트
    public static void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            Scanner scanner = new Scanner(System.in);
            cmd = scanner.nextLine();      // 명령 입력 받기

            switch (cmd) {
                case "종료":
                    return;   // 명령: "종료"일 때 앱 종료

                case "등록":
                    create();     // 명령: "등록"일 때 create() 메소드 실행
                    break;

                case "목록":
                    read();    // 명령: "목록"일 때 read() 메소드 실행
                    break;

                default:
                    if(cmd.startsWith("삭제?id=")){
                        delete();
                    }     // 명령: "삭제?id="로 시작할 때 delete() 메소드 실행
                    break;
            }
        }
    }

    // 명언 등록 메소드
    static void create(){

        System.out.print("명언 : ");
        Scanner scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();   // scanner로 명언, 작가 입력 받음

        cnt++;  // 등록된 명언의 개수 1씩 증가
        int id = cnt;

        Quotation q = new Quotation(id, content, author);
        qList.add(q);    // 리스트에 명언 객체 추가

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    // 명언 목록 출력 메소드
    static void read(){

        if(qList.isEmpty())
            System.out.println("등록된 명언이 없습니다.");

        else {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");
            for (int i = qList.size() - 1; i >= 0 ; i--) {
                Quotation q = qList.get(i);

                System.out.printf("%d / %s / %s\n", q.getId(), q.getAuthor(), q.getContent());
            }
        }
    }

    static void delete(){
        int deleteId = Integer.parseInt(cmd.substring("삭제?id=".length()));
        qList.remove(deleteId-1);
        System.out.printf("%d번 명언이 삭제되었습니다.\n", deleteId);
    }
}
