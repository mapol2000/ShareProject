package gteamproject.shere

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.CarouselView

class Share : Fragment() {

    //이미지 파일 경로 리스트
    var sampleImgs = listOf(
            R.drawable.test1,
            R.drawable.test2,
            R.drawable.test3,
            R.drawable.test4
    )


    var carselView = arrayOfNulls<CarouselView>(3)
    // 슬라이드 갯수에 따른 ID
    var carselViewId = arrayOf(R.id.carselView1, R.id.carselView2, R.id.carselView3)


    companion object{
        const val TAG : String = "로그"

        fun newInstance() : Share {
            return Share()
        }
    }

    // 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Shere_c")
    }

    // 프래그먼트를 안고있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "Shere_a")


    }

    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "Shere_v")

        val view = inflater.inflate(R.layout.share, container, false)



        // for-in 문으로 한번에 ID 등록하여 사용할 수 있게 해줌
        for (i in carselView.indices) {
            // 슬라이드 활성화
            carselView[i] = view.findViewById(carselViewId[i])
            // 각각의 슬라이드에 들어갈 사진 갯수 설정
            carselView[i]?.pageCount = sampleImgs.size
            // 각각의 슬라이드에 들어갈 사진 설정
            carselView[i]?.setImageListener { position, imageView ->
                imageView.setImageResource(sampleImgs[position])
            }
        }



        return view
    }
}