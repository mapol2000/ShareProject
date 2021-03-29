package gteamproject.shere.jdbc

import android.R
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.widget.EditText
import android.widget.TextView
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


class INSERTimg(val context: Context) : AsyncTask<String?, Void?, Void?>() {

    companion object{
        const val insertSQL ="insert into exPlus (user_id, file_num, title, pax, price, descript) values(?,?,?,?,?,?)"
    }

    override fun doInBackground(vararg params: String?): Void? {

        var uid = params[0].toString()
        var fnum = params[1].toString()
        var uptitle = params[2].toString()
        var uppax = params[3].toString()
        var upprice = params[4].toString()
        var updescript = params[5].toString()

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            conn = JDBCUtil.makeConn()
            pstmt = conn!!.prepareStatement(insertSQL)
            pstmt.setString(1, uid)
            pstmt.setString(2, fnum)
            pstmt.setString(3, uptitle)
            pstmt.setString(4,uppax)
            pstmt.setString(5,upprice)
            pstmt.setString(6,updescript)

            var cnt = pstmt.executeUpdate()
        } catch (se: SQLException) {
            println("insertEmp 에서 오류발생!!")
            se.printStackTrace()
        }
        // println(id?.text.toString())

        return null
    }
}