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

class SELECTE_DB_DT(val context: Context) : AsyncTask<UFDBVO?, UFDBVO?, DTDBVO?>() {


    var result1 = ""
    var result2 = ""

    var tv1:TextView? = null
    var tv2:TextView? = null

    companion object{
        const val select_DB_DT_sql ="SELECT * FROM exPlus WHERE user_id = ? and file_num = ?"
    }

    override fun onPreExecute() {
       // tv1 = (context as Activity).findViewById(R.id.homeDB1)
       // tv2 = (context as Activity).findViewById(R.id.homeDB1_1)
    }

    override fun doInBackground(vararg params: UFDBVO?): DTDBVO? {
        var vo = params[0] as UFDBVO

        var uid = vo.uid
        var fnum = vo.fnum

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        var title = StringBuilder()
        var pax = StringBuilder()
        var price = StringBuilder()
        var descript = StringBuilder()

        try{
            conn = JDBCUtil.makeConn()
            pstmt = conn!!.prepareStatement(select_DB_DT_sql)
            pstmt.setString(1, uid)
            pstmt.setString(2, fnum)
            rs = pstmt.executeQuery()

            while(rs.next()){
                title.append(rs.getString("title"))
                pax.append(rs.getString("pax"))
                price.append(rs.getString("price"))
                descript.append(rs.getString("descript"))
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }finally {
            JDBCUtil.destoryConn(conn, pstmt, rs)
        }

        var dvo = DTDBVO()
        dvo.title = title.toString()
        dvo.pax = pax.toString()
        dvo.price = price.toString()
        dvo.descript = descript.toString()

        return dvo
    }

    override fun onPostExecute(params: DTDBVO?) {
        //tv1?.setText(result1)
        //tv2?.setText(result2)

    }
}