package gteamproject.shere.jdbc

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import gteamproject.shere.Frag_Login
import gteamproject.shere.R
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class CHECKUSER_login(val context: Frag_Login) : AsyncTask<String?, Void?, Void?>() {

    var result = ""
    var tv : TextView? = null

    companion object{
        const val selectimgSQL ="select COUNT(user_id) from exUser where user_id = ? and user_pw = ?"
    }

    override fun onPreExecute() {
        tv = (context as Fragment).view!!.findViewById(R.id.checked_login)
    }
    override fun doInBackground(vararg params: String?): Void? {
        var uid = params[0].toString()
        var upw = params[1].toString()

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        var sb = StringBuilder()

        try{
            conn = JDBCUtil.makeConn()
            pstmt = conn!!.prepareStatement(selectimgSQL)
            pstmt.setString(1, uid)
            pstmt.setString(2, upw)
            rs = pstmt.executeQuery()

            while(rs.next()){
                sb.append(rs.getString("COUNT(user_id)"))
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

    override fun onPostExecute(params: Void?) {
        tv?.setText(result)
    }
}