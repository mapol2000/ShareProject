package gteamproject.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import gteamproject.shere.Magazine_Detail
import gteamproject.shere.R

class MagazineRecyclerAdapter (private var images: List<Int>, private var titles: List<String>, private var briefs: List<String>, private var publishers: List<String>) :
    RecyclerView.Adapter<MagazineRecyclerAdapter.ViewHolder>() {


    var magazineTitles = arrayOf("첫번째", "두번째", "세번째", "네번째", "다섯번째")


    inner class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.image)
        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemBrief: TextView = itemView.findViewById(R.id.brief)
        val itemPublisher: TextView = itemView.findViewById(R.id.publisher)


        init { // 클래스 생성되며 초기화로 setOnClicklistener 등록
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val activity = v.context as AppCompatActivity

                // 액티비티로 넘겨줄 데이터 설정
                val intent = Intent(v.context, Magazine_Detail::class.java)
                intent.putExtra("image", images[position].toString())

                // 아래 onBindViewHolder에 설정해둔 제목을 activity_magazine_detail에 보여줌
                intent.putExtra("title", itemTitle.text)
                // 하드코딩
//                intent.putExtra("title", titles[position])

                // 아래 onBindViewHolder에 설정해둔 내용을 activity_magazine_detail에 보여줌
                intent.putExtra("brief", itemBrief.text)
                // 하드코딩
//                intent.putExtra("brief", briefs[position])

                intent.putExtra("publisher", publishers[position])
                activity.startActivity(intent)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.magazine_cardview, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])

        // 리사이클러뷰 안에 카드뷰 제목
        holder.itemTitle.text = titles[position]
        // 하드코딩
//        holder.itemTitle.text = "제목 - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed pharetra nisl tristique, accumsan tellus eget, sollicitudin ligula. Integer augue ipsum, lacinia a commodo vel, maximus eget lacus. Morbi luctus vel massa in tincidunt. Ut sit amet viverra ex. Vivamus pharetra mi orci, ac fermentum ex finibus eget. Aenean ut malesuada eros. Cras pretium neque sit amet risus lacinia aliquam. Suspendisse condimentum dolor id lacinia pharetra. Fusce molestie orci odio, at aliquet ipsum auctor non. Quisque molestie pellentesque neque vitae laoreet. Etiam maximus sapien molestie diam fermentum, eu posuere leo suscipit. Nulla sed ultrices arcu, ut fringilla leo. Quisque mauris risus, fringilla quis tristique eu, ornare vel dolor. Aenean ac felis leo. Donec fringilla blandit semper. Suspendisse eu nulla maximus, fermentum orci sit amet, tincidunt felis."

        // 리사이클러뷰 안에 카드뷰 본문 내용
        holder.itemBrief.text = briefs[position]
        // 하드코딩
//        holder.itemBrief.text = "내용 - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed pharetra nisl tristique, accumsan tellus eget, sollicitudin ligula. Integer augue ipsum, lacinia a commodo vel, maximus eget lacus. Morbi luctus vel massa in tincidunt. Ut sit amet viverra ex. Vivamus pharetra mi orci, ac fermentum ex finibus eget. Aenean ut malesuada eros. Cras pretium neque sit amet risus lacinia aliquam. Suspendisse condimentum dolor id lacinia pharetra. Fusce molestie orci odio, at aliquet ipsum auctor non. Quisque molestie pellentesque neque vitae laoreet. Etiam maximus sapien molestie diam fermentum, eu posuere leo suscipit. Nulla sed ultrices arcu, ut fringilla leo. Quisque mauris risus, fringilla quis tristique eu, ornare vel dolor. Aenean ac felis leo. Donec fringilla blandit semper. Suspendisse eu nulla maximus, fermentum orci sit amet, tincidunt felis.\n" +
//                "\n" +
//                "Vivamus dignissim vitae nibh sit amet varius. Cras aliquet enim libero, rutrum tempor erat ornare dapibus. Integer vestibulum arcu velit, ac convallis justo semper sed. Duis vel felis augue. Aenean commodo tincidunt pretium. Donec posuere sapien eros, eget fringilla risus sagittis in. Mauris feugiat faucibus venenatis.\n" +
//                "\n" +
//                "Nullam dapibus tellus egestas accumsan laoreet. Maecenas volutpat dui sapien, a auctor ligula hendrerit nec. Maecenas blandit arcu urna, at porttitor mauris euismod nec. Nunc a facilisis lacus. Pellentesque imperdiet tellus vel magna vestibulum, ac finibus ligula ornare. Nunc convallis arcu in nibh dictum pellentesque. Morbi faucibus nibh augue, et dictum nulla tempor et.\n" +
//                "\n"

        holder.itemPublisher.text = publishers[position]

    }

    override fun getItemCount(): Int {
        return titles.size
    }
}

// TODO: 제목, 본문내용, 작성자 정규화식 해야함