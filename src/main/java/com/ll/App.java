package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    static String cmd;
    static int cnt = 0;    // 등록된 명언의 개수, 0부터 시작
    static List<Quotation> qList = new ArrayList<>();   // 명언 저장 리스트
    static List<Integer> deletedIds = new ArrayList<>(); // 삭제된 명언의 ID를 추적하는 리스트
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

                    else if (cmd.startsWith("수정?id=")) {
                        update();
                    }     // 명령: "수정?id="로 시작할 때 update() 메소드 실행
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

        // 리스트가 비어 있을 때
        if(qList.isEmpty())
            System.out.println("등록된 명언이 없습니다.");

        // 명언 목록 출력
        else {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            for (int i = qList.size() - 1; i >= 0 ; i--) {
                Quotation q = qList.get(i);

                System.out.printf("%d / %s / %s\n", q.getId(), q.getAuthor(), q.getContent());
            }
        }
    }
    // 명언 삭제 메소드
    static void delete() {

        try {
            // cmd에서 "삭제?id="를 잘라내고 나머지 값을 int로 변환
            int deleteId = Integer.parseInt(cmd.substring("삭제?id=".length()));

            // 이미 삭제된 명언을 다시 삭제하려고 한 경우
            if (deletedIds.contains(deleteId)) {
                System.out.println("이미 삭제된 명언입니다.");
                return;
            }

            boolean found = false;

            // qList를 순회하면서 id가 deleteId와 일치할 때 found를 true로
            for (int i = 0; i < qList.size(); i++) {
                Quotation q = qList.get(i);

                if (q.getId() == deleteId) {
                    qList.remove(i);
                    deletedIds.add(deleteId); // 삭제된 명언의 ID를 리스트에 추가

                    found = true;
                    System.out.printf("%d번 명언이 삭제되었습니다.\n", deleteId);
                    break;
                }
            }
            if (!found) {
                System.out.printf("%d번 명언은 존재하지 않습니다.\n", deleteId);
            }   // found가 false인 경우 메시지 출력
        }
        catch (NumberFormatException e) {
            System.out.println("올바른 명령 형식이 아닙니다.");
        }   // 삭제?id 값에 int로 변환할 수 없는 값이 들어왔을 때
    }

    static void update() {

        try {
            // cmd에서 "수정?id="를 잘라내고 나머지 값을 int로 변환
            int updateId = Integer.parseInt(cmd.substring("수정?id=".length()));

            // 이미 삭제된 명언을 수정하려고 한 경우
            if (deletedIds.contains(updateId)) {
                System.out.println("이미 삭제된 명언입니다.");
                return;
            }

            boolean found = false;

            // qList를 순회하면서 id가 updateId와 일치할 때 found를 true로
            for (int i = 0; i < qList.size(); i++) {
                Quotation q = qList.get(i);

                if (q.getId() == updateId) {
                    System.out.printf("명언(기존): %s\n", q.getContent());

                    System.out.print("명언: ");
                    Scanner scanner = new Scanner(System.in);
                    String newContent = scanner.nextLine();
                    q.setContent(newContent);     // 명언 수정

                    System.out.printf("작가(기존): %s\n", q.getAuthor());

                    System.out.print("작가: ");
                    String newAuthor = scanner.nextLine();
                    q.setAuthor(newAuthor);   // 작가 수정

                    found = true;
                    System.out.println("명언이 수정되었습니다.");
                }
            }
            if (!found) {
                System.out.printf("%d번 명언은 존재하지 않습니다.\n", updateId);
            }   // found가 false인 경우 메시지 출력
        }
        catch (NumberFormatException e) {
            System.out.println("올바른 명령 형식이 아닙니다.");
        }    // 수정?id 값에 int로 변환할 수 없는 값이 들어왔을 때
    }
}
