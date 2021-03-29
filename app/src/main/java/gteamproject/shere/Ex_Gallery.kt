package gteamproject.shere

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream


class Ex_Gallery : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    var imgView: ImageView? = null
    val TAG = "activity_gallery"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex_gallery)
    }


    fun loadImagefromGallery(view: View?) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    //이미지 선택작업을 후의 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {

            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {

                //data에서 절대경로로 이미지를 가져옴
                val uri: Uri? = data.data

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                val nh = (bitmap.height * (1024.0 / bitmap.width)).toInt()
                val scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true)

                imgView = findViewById<View>(R.id.imageView) as ImageView
                imgView!!.setImageBitmap(scaled)

                saveBitmapToJpeg(scaled)

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun saveBitmapToJpeg(scaled: Bitmap) {
        val imgName = "test.png"
        val tempFile = File(filesDir.absolutePath+"/sinwoo", imgName) //cachedir
        try {
            tempFile.createNewFile()
            val out = FileOutputStream(tempFile)
            scaled.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            Toast.makeText(applicationContext, "파일 저장 성공", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            Toast.makeText(applicationContext, "파일 저장 실패", Toast.LENGTH_SHORT).show()
        }
    }
}