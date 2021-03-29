package gteamproject.shere.jdbc

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class exSELECTEMP(val context: Context) : AsyncTask<Void?, Void?, Void?>() {

    var result = ""
    var tv : TextView? = null

    companion object{
        const val selectSQL ="select employee_id, first_name, last_name from EMPLOYEES"
        const val selectOneSQL ="select * from EMPLOYEES where employee_id = ?"
        const val selectDBSQL ="select photo_num(MAX) from EMPLOYEES where user_id = ?"
    }

    /*override fun onPreExecute() {
        tv = (context as Activity).findViewById(R.id.login_id)
    }*/
    override fun doInBackground(vararg params: Void?): Void? {
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        var sb = StringBuilder()

        try{
            conn = JDBCUtil.makeConn()
            pstmt = conn!!.prepareStatement(selectSQL)
            rs = pstmt.executeQuery()

            while(rs.next()){
                sb.append(rs.getString("employee_id")).append("/")
                sb.append(rs.getString("first_name")).append("/")
                sb.append(rs.getString("last_name")).append("\n")
            }
            Log.d("jdbc", sb.toString())
        } catch (ex: Exception){
            ex.printStackTrace()
        }finally {
            JDBCUtil.destoryConn(conn, pstmt, rs)
        }
        result = sb.toString()

        return null
    }

    /*override fun onPostExecute(params: Void?) {
        tv?.setText(result)
    }*/
}