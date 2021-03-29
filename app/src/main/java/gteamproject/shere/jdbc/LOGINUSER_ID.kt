package gteamproject.shere.jdbc

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import gteamproject.shere.R
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class LOGINUSER_ID(val context: Context) : AsyncTask<Void?, Void?, Void?>() {

    var result = ""
    var tv : TextView? = null

    companion object{
        const val selectSQL ="select user_id from exUser"
    }

    /*override fun onPreExecute() {
        tv = (context as Activity).findViewById(R.id.textview10)
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
                sb.append(rs.getString("user_id"))
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