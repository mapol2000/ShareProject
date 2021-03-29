package gteamproject.shere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Magazine_Detail : AppCompatActivity() {

    // 위젯 변수선언
    lateinit var detailTitle: TextView
    lateinit var detailPublisher: TextView
    lateinit var detailImage: ImageView
    lateinit var detailContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magazine__detail)

        // 위젯 변수선언
        detailTitle = findViewById(R.id.detailTitle)
        detailPublisher = findViewById(R.id.detailPublisher)
        detailImage = findViewById(R.id.detailImage)
        detailContent = findViewById(R.id.detailContent)

        // 인텐트로 받아오기
        detailTitle.text = intent.getStringExtra("title")
        detailPublisher.text = intent.getStringExtra("publisher")
        detailImage.setImageResource(intent.getStringExtra("image")!!.toInt())
        detailContent.text = intent.getStringExtra("brief")

    }
}

// ******* 매거진 화면에서 카드뷰 클릭했을시 상세 내용은 그냥 카드뷰에 있는걸 그대로 가져올 것임 *******
// ******* 따라서 매거진 화면에서 한번, 상세 내용에서 한번 이렇게 데이터를 두번 읽거나 하지 않고
// ******** Magazine.kt안에 addToList와 postToList를 통해 매거진 카드뷰에 읽어오기
// ******* DB 연결시 매거진 화면에서 읽어들이고 상세 화면으로 가면 데이터가 살아있을지 모르겠지만 테스트 요함 ********

// TODO: 제목, 본문내용, 작성자 정규화식 해야함