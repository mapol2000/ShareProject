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

class SELECTE_DB_HOME2(val context: Context) : AsyncTask<String?, Void?, Void?>() {


    var resultui = ""
    var resultfn = ""

    var tv1:TextView? = null
    var tv2:TextView? = null

    companion object{
        const val select_DB_HOME_sql ="SELECT user_id, file_num FROM exPlus WHERE file_num != 0 ORDER BY reg_date DESC LIMIT 1,1"
    }

    override fun onPreExecute() {
        // tv = (context as Activity).findViewById(R.id.img_num)
        tv1 = (context as Activity).findViewById(R.id.homeDB2)
        tv2 = (context as Activity).findViewById(R.id.homeDB2_2)
    }

    override fun doInBackground(vararg params: String?): Void? {

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        var uisb = StringBuilder()
        var fnsb = StringBuilder()

        try{
            conn = JDBCUtil.makeConn()
            pstmt = conn!!.prepareStatement(select_DB_HOME_sql)
            rs = pstmt.executeQuery()

            while(rs.next()){
                uisb.append(rs.getString("user_id"))
                fnsb.append(rs.getString("file_num"))
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }finally {
            JDBCUtil.destoryConn(conn, pstmt, rs)
        }
        resultui = uisb.toString()
        resultfn = fnsb.toString()

        Log.d("ui", "$resultui")
        Log.d("ui", "$resultfn")

        return null
    }

    override fun onPostExecute(params: Void?) {
        tv1?.setText(resultui)
        tv2?.setText(resultfn)

    }
}