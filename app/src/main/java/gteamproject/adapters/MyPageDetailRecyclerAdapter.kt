package gteamproject.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import gteamproject.shere.Magazine_Detail
import gteamproject.shere.R

class MyPageDetailRecyclerAdapter (private var images: List<Int>, private var titles: List<String>, private var address: List<String>, private var duration: List<String>) :
RecyclerView.Adapter<MyPageDetailRecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.image)
        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemAdress: TextView = itemView.findViewById(R.id.adress)
        val itemDurations: TextView = itemView.findViewById(R.id.duration)

        init { // 클래스 생성되며 초기화로 setOnClicklistener 등록
            itemView.setOnClickListener { v: View ->
//                Toast.makeText(itemView.context, "눌렀어요", Toast.LENGTH_SHORT).show()
                println("눌렀어요")
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rev_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemImage.setImageResource(images[position])
        holder.itemTitle.text = titles[position]
        holder.itemAdress.text = address[position]
        holder.itemDurations.text = "예약일자 : 2020/08/14 ~ 2020/08/20"

    }

    override fun getItemCount(): Int {
        return titles.size
    }
}

// TODO: 제목, 본문내용, 작성자 정규화식 해야함