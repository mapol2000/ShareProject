package gteamproject.shere.jdbc

import android.app.Activity
import android.os.AsyncTask
import android.widget.TextView
import gteamproject.shere.LoginRegister
import gteamproject.shere.R
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


class INSERTUSER_plus0(val cid: LoginRegister) : AsyncTask<Void?, Void?, Void?>() {

    var id : TextView? = null

    companion object{
        const val insertSQL ="insert into exPlus (user_id,file_num) values(?,0)"
    }

    override fun onPreExecute() {
        id = (cid as Activity).findViewById(R.id.userName2)
    }

    override fun doInBackground(vararg params: Void?): Void? {

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            conn = JDBCUtil.makeConn()
            pstmt = conn!!.prepareStatement(insertSQL)
            pstmt.setString(1, id?.text.toString())
            // pstmt.setString(2, emp.getFname())

            var cnt = pstmt.executeUpdate()
        } catch (se: SQLException) {
            println("insertEmp 에서 오류발생!!")
            se.printStackTrace()
        }
        // println(id?.text.toString())

        return null
    }
}