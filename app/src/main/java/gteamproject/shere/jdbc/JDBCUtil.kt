package gteamproject.shere.jdbc

import java.sql.*

object JDBCUtil {
    const val DRV: String = "org.mariadb.jdbc.Driver"
    const val URL: String = "jdbc:mariadb://mariadb.c10pt4mcai0p.ap-northeast-2.rds.amazonaws.com:3306/Gteam"
    const val USR: String = "gteam"
    const val PWD: String = "gteam2021"

    fun makeConn(): Connection? {
        var conn: Connection? = null
        try {
            Class.forName(DRV)
            conn = DriverManager.getConnection(URL, USR, PWD)
        } catch (e: ClassNotFoundException) {
            println("JDBC 드라이버가 없어요!")
        } catch (e: SQLException) {
            println("JDBC 연결 실패!")
        }
        return conn
    }
    fun destoryConn(conn: Connection?, pstmt: PreparedStatement?) {
        if (pstmt != null) try {
            pstmt.close()
        } catch (ex: SQLException) {
        }
        if (conn != null) try {
            conn.close()
        } catch (ex: SQLException) {
        }
    }
    fun destoryConn(conn: Connection?, pstmt: PreparedStatement?, rs: ResultSet?) {
        if (rs != null) try {
            rs.close()
        } catch (ex: SQLException) {
        }
        destoryConn(conn, pstmt)
    }


}