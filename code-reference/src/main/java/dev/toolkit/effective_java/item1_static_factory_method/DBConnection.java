package dev.toolkit.effective_java.item1_static_factory_method;

/**
 * [ 객체 생성 비용이 크거나 복잡한 객체 생성을 단순화 및 캡술화 ]
 * 객체 생성할 때 내부적으로 복잡한 설정이 들어가거나, 객체 생성 과정을 캡슐화 하고 싶을 경우 유용하다.
 * <p>
 * - 생성자에 들어가야할 값은 클라이언트가 직접 입력해야 하지만
 * - 정적 팩터리 메서드를 활용하여 기본 설정 등을 추가하여
 *    → 사용자는 `createWithDefaultConfig()` 만 호출 하면 된다.
 * <p>
 * - 객체 생성 과정을 캡슐화하고 싶을 때
 * - 생성자 매개변수에 클라이언트에게 받지 않는 특정 설정이 필요할 경우
 * - 정적 팩터리 메서드안에서 처리 후 생성자를 호출하고
 *    → 클라이언트는 정적 팩터리 메서드만 호출하면 된다.
 *
 */
public class DBConnection {
    private String url;
    private String userName;
    private String password;

    public DBConnection(String url, String userName, String password) {
        System.out.println("DB Connection created");
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public static DBConnection createWithDefaultConfig() {
        return new DBConnection("jdbc:mysql://localhost:3306/db", "user", "1234");
    }

    @Override
    public String toString() {
        return "DBConnection{" +
                "url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

class DBConnectionMain {
    public static void main(String[] args) {
        DBConnection defaultConnection = DBConnection.createWithDefaultConfig();
        DBConnection adminConnection = new DBConnection("jdbc:mysql://localhost:3306/db", "admin", "1234");

        System.out.println(defaultConnection);
        System.out.println(adminConnection);
    }
}
