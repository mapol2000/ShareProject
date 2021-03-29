package gteamproject.shere

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Mypage : Fragment() {

    lateinit var yourPlace: TextView
    lateinit var myPlace: TextView

    lateinit var faq: Button
    lateinit var policy: Button
    lateinit var versionInfo: Button
    lateinit var logoutBtn: Button

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : Mypage {
            return Mypage()
        }
    }

    // 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Mypage_c")
    }

    // 프래그먼트를 안고있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "Mypage_a")
    }

    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "Mypage_v")

        val view = inflater.inflate(R.layout.mypage, container, false)

        // 예약내역
        yourPlace = view.findViewById(R.id.yourPlace)
        myPlace = view.findViewById(R.id.myPlace)

        // 아래 버튼들
        faq = view.findViewById(R.id.faq)
        policy = view.findViewById(R.id.policy)
        versionInfo = view.findViewById(R.id.versionInfo)
        logoutBtn = view.findViewById(R.id.logoutBtn)

        // 예약내역 액션
        yourPlace.setOnClickListener {
            val intent = Intent(context, Mypage_Detail::class.java)
            startActivity(intent)
        }

        myPlace.setOnClickListener {
            val intent = Intent(context, Mypage_Detail::class.java)
            startActivity(intent)
        }

        // 아래 버튼들 액션
        faq.setOnClickListener {
            val intent = Intent(context, FAQ::class.java)
            startActivity(intent)
        }

        policy.setOnClickListener {
            val intent = Intent(context, Policy::class.java)
            startActivity(intent)
        }

//        myList.setOnClickListener {
//            val intent = Intent(context, Mypage_Detail::class.java)
//            startActivity(intent)
//        }

        return view
    }
}