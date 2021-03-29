package gteamproject.shere

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import gteamproject.shere.jdbc.DTDBVO
import gteamproject.shere.jdbc.SELECTE_DB_DT
import gteamproject.shere.jdbc.UFDBVO

class ShareDetail : AppCompatActivity() {

    lateinit var sh_Info : TextView
    lateinit var sh_Info2 : TextView
    lateinit var sh_Review : TextView
    lateinit var detail_btnCall: ImageButton

    lateinit var DT_title : TextView
    lateinit var DT_pax : TextView
    lateinit var DT_price : TextView
    lateinit var DT_descript : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_detail)

        var vo:UFDBVO = UFDBVO()

        vo.uid = intent.getStringExtra("dbuser_id")?.trim().toString()
        vo.fnum = intent.getStringExtra("dbfnum")?.trim().toString()

        var dvo = SELECTE_DB_DT(this).execute(vo).get() as DTDBVO


        DT_title = findViewById(R.id.DTV_title)
        DT_pax = findViewById(R.id.DTV_pax)
        DT_price = findViewById(R.id.DTV_price)


        DT_title.setText("${dvo.title}")
        DT_pax.setText("${dvo.pax}")
        DT_price.setText("${dvo.price}")



        //전화걸기 구현
        var btnCall: ImageButton = findViewById(R.id.detail_btnCall)
        var phoneNum: String = "tel:"

        btnCall.setOnClickListener {
            phoneNum += "07066667777"
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(phoneNum)
            startActivity(intent)

            //초기화
            phoneNum = "tel:"
        }


        //정보 프레그먼트
        initvar()

        setFrag(0)

        sh_Info.setOnClickListener {
            setFrag(0)
        }

        sh_Info2.setOnClickListener {
            setFrag(1)
        }

        sh_Review.setOnClickListener {
            setFrag(2)
        }


    }

    fun initvar() {
        sh_Info = findViewById(R.id.sh_Info)
        sh_Info2 = findViewById(R.id.sh_Info2)
        sh_Review = findViewById(R.id.sh_Review)
    }

    private fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.sh_detail_frame, ShareInfo()).commit()
            }
            1 -> {
                ft.replace(R.id.sh_detail_frame, ShareInfo2()).commit()
            }
            2 -> {
                ft.replace(R.id.sh_detail_frame, ShareReview()).commit()
            }
        }

    }

}