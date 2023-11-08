package com.ll;

//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    static String cmd;
    static List<Quotation> qList = new ArrayList<>();   // 명언 저장 리스트
    static List<Integer> delList = new ArrayList<>(); // 삭제된 명언의 ID를 추적하는 리스트
    static String dataFileName = "quotations.dat"; // 데이터 파일
    //static ObjectMapper objectMapper = new ObjectMapper();

    public static void run() {
        loadData(qList); // 애플리케이션 시작 시 데이터 파일에서 명언 데이터 로드
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            Scanner scanner = new Scanner(System.in);
            cmd = scanner.nextLine();      // 명령 입력 받기

            switch (cmd) {
                case "종료":
                    saveData(qList); // 애플리케이션 종료 시 파일에 데이터 저장
                    return;   // 명령: "종료"일 때 앱 종료

                case "등록":
                    create();     // 명령: "등록"일 때 create() 메소드 실행
                    break;

                case "목록":
                    read();    // 명령: "목록"일 때 read() 메소드 실행
                    break;

//                case "빌드":
//                    build();
//                    break;

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

    // 사용 가능한 ID 생성
    static int generateId() {
        int maxId = 0;

        // 기존 명언 목록에서 가장 큰 ID를 찾음
        for (Quotation q : qList) {
            if (q.getId() > maxId) {
                maxId = q.getId();
            }
        }

        // 중복 ID를 방지하기 위해 가장 큰 ID에 1을 더한 값을 반환
        return maxId + 1;
    }

    // 명언 등록 메소드
    static void create(){

        System.out.print("명언 : ");
        Scanner scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();   // scanner로 명언, 작가 입력 받음

        int id = generateId();
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

    // 삭제, 수정을 위해 특정 ID를 가진 명언을 찾아내는 메소드
    static Quotation findQuotationById(int id) {
        for (Quotation q : qList) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }

    // 명언 삭제 메소드
    static void delete() {
        try {
            // cmd에서 "삭제?id="를 잘라내고 나머지 값을 int로 변환
            int deleteId = Integer.parseInt(cmd.substring("삭제?id=".length()));
            // id가 deleteId인 명언을 findQuotationById()를 이용해서 찾아내고 toDelete에 할당
            Quotation toDelete = findQuotationById(deleteId);

            if (toDelete != null) {
                qList.remove(toDelete); // 명언 삭제
                delList.add(deleteId); // 삭제된 id를 관리하는 리스트에 해당 id 추가
                System.out.printf("%d번 명언이 삭제되었습니다.\n", deleteId);
            } else {
                System.out.printf("%d번 명언은 존재하지 않습니다.\n", deleteId);
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 명령 형식이 아닙니다.");
        }  // 삭제?id 값에 int로 변환할 수 없는 값이 들어왔을 때
    }

    static void update() {

        try {
            // cmd에서 "수정?id="를 잘라내고 나머지 값을 int로 변환
            int updateId = Integer.parseInt(cmd.substring("수정?id=".length()));
            // id가 updateId인 명언을 findQuotationById()를 이용해서 찾아내고 toUpdate에 할당
            Quotation toUpdate = findQuotationById(updateId);

            if (toUpdate != null) {
                System.out.printf("명언(기존): %s\n", toUpdate.getContent());
                System.out.print("명언: ");
                Scanner scanner = new Scanner(System.in);
                String newContent = scanner.nextLine();
                toUpdate.setContent(newContent);     // 명언 수정

                System.out.printf("작가(기존): %s\n", toUpdate.getAuthor());
                System.out.print("작가: ");
                String newAuthor = scanner.nextLine();
                toUpdate.setAuthor(newAuthor);   // 작가 수정

                System.out.println("명언이 수정되었습니다.");
            } else {
                System.out.printf("%d번 명언은 존재하지 않습니다.\n", updateId);
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 명령 형식이 아닙니다.");
        }    // 수정?id 값에 int로 변환할 수 없는 값이 들어왔을 때
    }


    // 명언 데이터를 파일에 저장
    static void saveData(List<Quotation> qList) {
        // 파일에 데이터를 쓰기 위해 FileOutputStream 생성, 객체를 직렬화하기 위해 ObjectOutputStream 생성하고 oos에 할당
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFileName))) {
            oos.writeObject(qList);  // 변수 oss를 통해 qList를 파일에 저장
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    static void saveData(List<Quotation> qList) {
//        try (FileWriter fileWriter = new FileWriter(dataFileName)) {
//            String json = toJson(qList);
//            fileWriter.write(json);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // 파일에서 명언 데이터 로드
    static void loadData(List<Quotation> qList) {
        // 파일에 데이터를 읽기 위해 FileInputStream 생성, 객체를 역직렬화하기 위해 ObjectInputStream 생성
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFileName))) {
            List<Quotation> loadedQuotations = (List<Quotation>) ois.readObject(); // 역직렬화된 객체 저장
            qList.clear();
            qList.addAll(loadedQuotations); // qList에 읽어온 명언 데이터 추가
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    static void loadData(List<Quotation> qList) {
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFileName), "UTF-8"))) {
//            StringBuilder sb = new StringBuilder();
//            int ch;
//            while ((ch = bufferedReader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            String json = sb.toString();
//            List<Quotation> loadedQuotations = fromJson(json);
//            qList.clear();
//            qList.addAll(loadedQuotations);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    static void build() {
//        try {
//            String json = toJson(qList); // 명언 목록을 JSON 문자열로 변환
//            saveJsonToFile(json, "data.json"); // JSON 문자열을 파일에 저장
//            System.out.println("데이터가 성공적으로 저장되었습니다.");
//        } catch (Exception e) {
//            System.out.println("데이터 저장 중 오류가 발생했습니다.");
//            e.printStackTrace();
//        }
//    }
//
//    // 명언을 JSON 형식의 문자열로 변환
//    static String toJson(List<Quotation> qList) {
//        try {
//            return objectMapper.writeValueAsString(qList);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // JSON 문자열을 명언 목록으로 변환
//    static List<Quotation> fromJson(String json) {
//        try {
//            return objectMapper.readValue(json, new TypeReference<List<Quotation>>() {});
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // JSON 문자열을 파일에 저장
//    static void saveJsonToFile(String json, String fileName) {
//        try (FileWriter fileWriter = new FileWriter(fileName)) {
//            fileWriter.write(json);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
