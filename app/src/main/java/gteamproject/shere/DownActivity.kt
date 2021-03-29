package gteamproject.shere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import java.io.File

class DownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down)
    }

    fun onDownloadClick(v: View) {
        downloadWithTransferUtility("sinwoo","1")
        // 클릭 말고 함수만 들어가게끔
        // DB로 reg_date 검색해서 최신순으로 또다른 넘버링주기( 이 넘버링은 DB(x) 기능으로만(o) ) 1번 2번 3번 4번 뜨게 하고 reg_date 순으로 user_id 입력되게하기
        // 이 기능은 홈 프래그먼트 실행시 적용되게끔 하기 - 장소쉐어도 마찬가지

        // 홈 or 디테일을 누르면 관련 DB 찾아서 상세정보 목록 출력
    }
    fun onUploadClick(v: View) {
        uploadWithTransferUtility("sinwoo","2")
        // useruid = 각 계정별 폴더를 만들기위해, fileNamae = 숫자를 두어 게시글 삭제시 DB에 있는
        // 사진 게시글 넘버링을 삭제하면서
        // 다시 순차대로 게시사진을 덮어씌워 기존 게시글에 대한 데이터가 지워지는(덮어씌워지는)효과를 위한 넘버링
    }

    private fun downloadWithTransferUtility(useruid: String, fileName: String) {
        // Cognito 샘플 코드. CredentialsProvider 객체 생성
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
        val downloadObserver = transferUtility.download("${useruid}PATH/${fileName}", File(filesDir.absolutePath + "/${useruid}/${fileName}.bmp"))

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

    /////////////////////////////////////

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
        val uploadObserver = transferUtility.upload("${useruid}PATH/${fileName}", File(filesDir.absolutePath + "/${useruid}/${fileName}.png"))
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


}