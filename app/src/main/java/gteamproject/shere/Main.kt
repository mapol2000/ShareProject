package gteamproject.shere

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.google.android.material.bottomnavigation.BottomNavigationView
import gteamproject.communicator.Communicator
import gteamproject.shere.jdbc.*
import java.io.File
import java.io.FileOutputStream

class Main : AppCompatActivity(), Communicator {

    //MAIN 변수 시작
    private lateinit var home: Home
    private lateinit var share: Share
    private lateinit var magazine: Magazine
    private lateinit var mygpage: Mypage
    private lateinit var plus: Plus
    //MAIN 변수 끝


    //HOME 변수 시작
    var exhomedbid1: TextView? = null
    var exhomedbid1_1: TextView? = null
    var homedbid1 = ""
    var homedbid1_1 = ""

    var exhomedbid2: TextView? = null
    var exhomedbid2_2: TextView? = null
    var homedbid2 = ""
    var homedbid2_2 = ""

    var exhomedbid3: TextView? = null
    var exhomedbid3_3: TextView? = null
    var homedbid3 = ""
    var homedbid3_3 = ""

    //HOME 변수 끝

    //PLUS 변수 시작
    private val PICK_IMAGE_REQUEST = 1
    var imgView: ImageView? = null
    var upDB_Title: TextView? = null
    var upDB_Pax: TextView? = null
    var upDB_Price: TextView? = null
    var upDB_Descript: TextView? = null

    var up_Title = ""
    var up_Pax = ""
    var up_Price = ""
    var up_Descript = ""

    val TAG = ""

    var inum: TextView? = null
    var fnum = ""
    //PLUS 변수 끝


    //MAIN 액티비티 정의
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        SELECTE_DB_HOME(this).execute()
        SELECTE_DB_HOME2(this).execute()
        SELECTE_DB_HOME3(this).execute()

        exhomedbid1 = findViewById(R.id.homeDB1)
        exhomedbid1_1 = findViewById(R.id.homeDB1_1)

        exhomedbid2 = findViewById(R.id.homeDB2)
        exhomedbid2_2 = findViewById(R.id.homeDB2_2)

        exhomedbid3 = findViewById(R.id.homeDB3)
        exhomedbid3_3 = findViewById(R.id.homeDB3_3)

        Handler().postDelayed(Runnable {

        homedbid1 = exhomedbid1?.text.toString()
        homedbid1_1 = exhomedbid1_1?.text.toString()

            homedbid2 = exhomedbid2?.text.toString()
            homedbid2_2 = exhomedbid2_2?.text.toString()

            homedbid3 = exhomedbid3?.text.toString()
            homedbid3_3 = exhomedbid3_3?.text.toString()

            downloadHOMEimg("$homedbid1","$homedbid1_1","1")
            downloadHOMEimg("$homedbid2","$homedbid2_2","2")
            downloadHOMEimg("$homedbid3","$homedbid3_3","3")

            Handler().postDelayed(Runnable {

                val bottomNavigationView = findViewById<View>(R.id.bottom_nav) as BottomNavigationView
                bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)

                home = Home.newInstance()
                supportFragmentManager.beginTransaction().add(R.id.fragment_frame, home).commit()
            }, 1500)

        }, 1000)



        //첫번째 자료 클릭시

        //두번째 자료 클릭시

        //세번째 자료 클릭시
    }

    private val onBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, home).commit()
            }
            R.id.menu_share -> {
                share = Share.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, share).commit()
            }

            R.id.menu_magazine -> {
                magazine = Magazine.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, magazine).commit()
            }

            R.id.menu_mypage -> {
                mygpage = Mypage.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, mygpage).commit()
            }

            R.id.menu_plus -> {
                plus = Plus.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, plus).commit()
            }
        }
        true
    }

    fun upLoad(v: View){
        val intent = Intent(this, DownActivity::class.java)
        startActivity(intent)
    }
    fun galleryLoad(v: View){
        val intent = Intent(this, Ex_Gallery::class.java)
        startActivity(intent)
    }






    //PLUS 프래그먼트 관련 함수
    //PLUS
    fun loadImagefromGallery(view: View?) {

        val dbuserid = intent.getStringExtra("user_id")?.trim().toString()  // 인텐트로 전달받은 유저 ID 값

        SELECTEimg(this).execute("$dbuserid")      // DB로 ID별 최대넘버 찾기

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

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

                imgView = findViewById<View>(R.id.imageView2) as ImageView
                imgView!!.setImageBitmap(scaled)

                inum = findViewById(R.id.img_num)                               // 이미지 넘버확인
                println(Integer.parseInt(inum?.text.toString()))
                fnum = (Integer.parseInt(inum?.text.toString())).toString() // 파일이름으로 쓸 넘버맥이기

                saveBitmapToJpeg(scaled)

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            // 계정 생성시 DB에 exPlus 에 데이터 0 을 넣어주지 않으면 NULL 값이 반환되면서 Oops 오류가 발생
        }
    }

    private fun saveBitmapToJpeg(scaled: Bitmap) {
        val imgName = "${fnum}.png"     // 파일넘버(게시글넘버).png
        val tempFile = File(filesDir.absolutePath, imgName) //cachedir
        try {
            tempFile.createNewFile()
            val out = FileOutputStream(tempFile)
            scaled.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            Toast.makeText(applicationContext, "이미지 불러오기 성공", Toast.LENGTH_SHORT).show()        // 사실은 파일 저장 성공여부
        } catch (e: java.lang.Exception) {
            Toast.makeText(applicationContext, "이미지 불러오기 실패", Toast.LENGTH_SHORT).show()        // 사실은 파일 저장 성공여부
        }
    }

    fun onUploadClick(v: View) {

        val dbuserid = intent.getStringExtra("user_id")?.trim().toString()          // 인텐트로 전달받은 유저 ID 값

        upDB_Title = findViewById(R.id.tv_upTitle)
        upDB_Pax = findViewById(R.id.pax)
        upDB_Price = findViewById(R.id.price)
        upDB_Descript = findViewById(R.id.description)

        up_Title = upDB_Title!!.text.trim().toString()
        up_Pax = upDB_Pax!!.text.trim().toString()
        up_Price = upDB_Price!!.text.trim().toString()
        up_Descript = upDB_Descript!!.text.trim().toString()

        println(upDB_Title!!.text.trim().toString())

        uploadWithTransferUtility("$dbuserid", "$fnum")
        // useruid = 각 계정별 폴더를 만들기위해, fileNamae = 숫자를 두어 게시글 삭제시 DB에 있는
        // 사진 게시글 넘버링을 삭제하면서
        // 다시 순차대로 게시사진을 덮어씌워 기존 게시글에 대한 데이터가 지워지는(덮어씌워지는)효과를 위한 넘버링


        INSERTimg(this).execute("$dbuserid", "$fnum", "$up_Title","$up_Pax","$up_Price","$up_Descript") // 업로드시 게시판 DB 데이터 증가

        home = Home.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, home).commit()
    }

    private fun uploadWithTransferUtility(useruid: String, fileName: String) {

        val credentialsProvider = CognitoCachingCredentialsProvider(
                applicationContext,
                "ap-northeast-2:9df3836a-339e-4bb0-9249-3ed02544c68e", // 자격 증명 풀 ID
                Regions.AP_NORTHEAST_2 // 리전
        )

        TransferNetworkLossHandler.getInstance(applicationContext)

        val transferUtility = TransferUtility.builder()
                .context(applicationContext) //GSApplicationClass.getInstance()
                .defaultBucket("gshere") //Bucket_Name
                .s3Client(AmazonS3Client(credentialsProvider, Region.getRegion(Regions.AP_NORTHEAST_2)))
                .build()

        /* Store the new created Image file path */

        //val uploadObserver = transferUtility.upload("BUCKET_PATH/${fileName}", file, CannedAccessControlList.PublicRead)
        val uploadObserver = transferUtility.upload("${useruid}PATH/${fileName}", File(filesDir.absolutePath + "/${fileName}.png"))
        //${useruid}PATH/${fileName} = 스토리지에 저장될 파일경로, filesDir.absolutePath + "${useruid}/2.png" = 내 파일 경로

        //CannedAccessControlList.PublicRead 읽기 권한 추가

        // Attach a listener to the observer
        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                }
            }

            override fun onProgressChanged(id: Int, current: Long, total: Long) {
                val done = (((current.toDouble() / total) * 100.0).toInt())
                Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done")
            }

            override fun onError(id: Int, ex: Exception) {
                Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX: ${ex.message.toString()}")
            }
        })

        // If you prefer to long-poll for updates
        if (uploadObserver.state == TransferState.COMPLETED) {
            /* Handle completion */

        }
    }

    //쉐어디테일 이동
    fun gospView1(v: View){
        val intent = Intent(this, ShareDetail::class.java)
        startActivity(intent)
    }

    fun gotoDB1(v:View){
        exhomedbid1 = findViewById(R.id.homeDB1)
        exhomedbid1_1 = findViewById(R.id.homeDB1_1)

        homedbid1 = exhomedbid1?.text.toString()
        homedbid1_1 = exhomedbid1_1?.text.toString()

        val intent = Intent(this, ShareDetail::class.java)
        intent.putExtra("dbuser_id", "$homedbid1")
        intent.putExtra("dbfnum","$homedbid1_1")
        startActivity(intent)
    }

    fun logout(v:View){
        val intent = Intent(this, LoginRegister::class.java)
        startActivity(intent)
        finish()
    }

    // Communicator 인터페이스에 있는 메서드
    override fun moveFragtoFrag() {
        val share = Share()
        // 이 메서드를 사용하는 프래그먼트는 shere 프래그먼트로 이동함
        this.supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, share).commit()
    }

    /////////////////////////////////////////////////////////////////////다운로드///////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////다운로드///////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////다운로드///////////////////////////////////////////////////////////////

    private fun downloadHOMEimg ( useruid: String, fileName: String, cacheName: String) {
        //  샘플 코드. CredentialsProvider 객체 생성
        val credentialsProvider = CognitoCachingCredentialsProvider(
                applicationContext,
                "ap-northeast-2:9df3836a-339e-4bb0-9249-3ed02544c68e", // 자격 증명 풀 ID
                Regions.AP_NORTHEAST_2 // 리전
        )

        // 반드시 호출해야 한다.
        TransferNetworkLossHandler.getInstance(applicationContext)

        // TransferUtility 객체 생성
        val transferUtility = TransferUtility.builder()
                .context(applicationContext)
                .defaultBucket("gshere") // 디폴트 버킷 이름.
                .s3Client(AmazonS3Client(credentialsProvider, Region.getRegion(Regions.AP_NORTHEAST_2)))
                .build()

        // 다운로드 실행. object: "SomeFile.mp4". 두 번째 파라메터는 Local 경로 File 객체.
        val downloadObserver = transferUtility.download("${useruid}PATH/${fileName}", File(filesDir.absolutePath + "/home/${cacheName}.bmp"))

        // 다운로드 과정을 알 수 있도록 Listener 를 추가할 수 있다.
        downloadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    Log.d("AWS", "DOWNLOAD Completed!")
                }
            }

            override fun onProgressChanged(id: Int, current: Long, total: Long) {
                try {
                    val done = (((current.toDouble() / total) * 100.0).toInt()) //as Int
                    Log.d("AWS", "DOWNLOAD - - ID: $id, percent done = $done")
                }
                catch (e: Exception) {
                    Log.d("AWS", "Trouble calculating progress percent", e)
                }
            }

            override fun onError(id: Int, ex: Exception) {
                Log.d("AWS", "DOWNLOAD ERROR - - ID: $id - - EX: ${ex.message.toString()}")
            }
        })
    }

























}