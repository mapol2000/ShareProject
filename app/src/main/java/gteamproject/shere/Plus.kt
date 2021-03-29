package gteamproject.shere

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class Plus : Fragment() {

    lateinit var pax: TextView              //인원수
    lateinit var seekBar: SeekBar
    lateinit var tv_upTitle: EditText       //제목
    lateinit var price: EditText            // 가격
    lateinit var description: EditText      // 기본정보

    var startPoint = 0
    var endPoint = 0

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : Plus {
            return Plus()
        }
    }

    // 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Plus_c")
    }

    // 프래그먼트를 안고있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "Plus_a")
    }

    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "Plus_v")

        val view = inflater.inflate(R.layout.plus, container, false)

        pax = view.findViewById(R.id.pax)
        seekBar = view.findViewById(R.id.seekBar)
        tv_upTitle = view.findViewById(R.id.tv_upTitle)
        price = view.findViewById(R.id.price)
        description = view.findViewById(R.id.description)



        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pax.text = "${progress}명"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                startPoint = seekBar?.progress!!
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                endPoint = seekBar?.progress!!
                if (seekBar.progress == 50) {
                    Toast.makeText(context, "50명 이상은 따로 연락 바랍니다", Toast.LENGTH_SHORT).show()
                }
            }

        })

        return view
    }
}