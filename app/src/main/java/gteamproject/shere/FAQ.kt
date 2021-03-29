package gteamproject.shere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
//import kotlinx.android.synthetic.main.activity_main.*

class FAQ : AppCompatActivity() {

    var viewList = ArrayList<View>()
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        viewList.add(layoutInflater.inflate(R.layout.fragment_qmember, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_qresandpay, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_qrepay, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_qetc, null))

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        viewPager.adapter = pagerAdapter()

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.setText("회원")
        tabLayout.getTabAt(1)?.setText("예약 및 결제")
        tabLayout.getTabAt(2)?.setText("취소 및 환불")
        tabLayout.getTabAt(3)?.setText("기타")
    }

    inner class pagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any) = view == `object`

        override fun getCount() = viewList.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var curView = viewList[position]
            viewPager.addView(curView)
            return curView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            viewPager.removeView(`object` as View)
        }
    }
}

