package gteamproject.shere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gteamproject.adapters.MagazineRecyclerAdapter
import gteamproject.adapters.MyPageDetailRecyclerAdapter

class Mypage_Detail : AppCompatActivity() {

    // 카드뷰에 사용할 변수 선언
    private var imageList = mutableListOf<Int>()
    private var titleList = mutableListOf<String>()
    private var adressList = mutableListOf<String>()
    private var durationList = mutableListOf<String>()

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage__detail)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyPageDetailRecyclerAdapter(imageList, titleList, adressList, durationList)

        postToList()

    }

    private fun addToList(image: Int, title: String, adress: String, duration: String) {
        imageList.add(image)
        titleList.add(title)
        adressList.add(adress)
        durationList.add(duration)
    }

    // DB에 저장되어있는 매거진 글 갯수로 설정해야 함
    private fun postToList() {
        for (i in 1..25) {
            addToList(R.drawable.paris, "이름 $i", "주소 $i", "예약기간 $i")
        }

    }

}