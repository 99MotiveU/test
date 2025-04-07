package MafiaG;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/mafia_game";
    private static final String USER = ""; // 본인 DB 유저명
    private static final String PASSWORD = ""; // 본인 DB 비밀번호

    // 유저의 현재 점수 가져오기
    public static int getUserScore(String username) {
        int score = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT 누적점수 FROM users WHERE 회원아이디 = ?")) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                score = rs.getInt("누적점수");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    // 유저의 점수 업데이트 (게임 후 반영)
    public static void updateUserScore(String username, int scoreToAdd) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE users SET 누적점수 = 누적점수 + ? WHERE 회원아이디 = ?")) {

            pstmt.setInt(1, scoreToAdd);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 로그아웃 (종료 전 처리)
    public static void logoutUser(String username) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE users SET last_login = NOW() WHERE 회원아이디 = ?")) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
