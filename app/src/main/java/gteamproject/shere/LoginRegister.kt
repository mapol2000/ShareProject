package gteamproject.shere

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import gteamproject.shere.jdbc.CHECKUSER
import gteamproject.shere.jdbc.INSERTUSER
import gteamproject.shere.jdbc.INSERTUSER_plus0
import java.util.regex.Pattern

class LoginRegister : AppCompatActivity() {

    lateinit var textSign : TextView
    lateinit var textRegister : TextView

    var checkpoint = 0
    
    // 회원가입 변수 정의
    lateinit var email : EditText
    lateinit var userName2 : EditText
    lateinit var password2 : EditText
    lateinit var chkAgree : CheckBox
    lateinit var btnSignup : Button
    // 회원가입 변수 정의 끝


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        initvar()

        setFrag(0)

        textSign.setOnClickListener {
            setFrag(0)
            textSign.setTypeface(null, Typeface.BOLD)
            textRegister.setTypeface(null, Typeface.NORMAL)
        }

        textRegister.setOnClickListener {
            setFrag(1)
            textRegister.setTypeface(null, Typeface.BOLD)
            textSign.setTypeface(null, Typeface.NORMAL)
        }


    }

    fun initvar() {
        textSign = findViewById(R.id.textSign)
        textRegister = findViewById(R.id.textRegister)
    }

    private fun setFrag(i: Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(i)
        {
            0 -> {
                ft.replace(R.id.main_frame, Frag_Login()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, Frag_Register()).commit()
            }
        }

    }

    fun goLogin(v: View){
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }



    // 회원가입 관련 함수 정의
    fun upSign(v: View){

        email = findViewById(R.id.email)
        userName2 = findViewById(R.id.userName2)
        password2 = findViewById(R.id.password2)
        chkAgree = findViewById(R.id.chkAgree)
        btnSignup = findViewById(R.id.btnSignup)

        val inputEmail = email.text.trim().toString()
        val inputName = userName2.text.trim().toString()
        val inputPassword = password2.text.trim().toString()
        val inputChk = chkAgree.isChecked

        if(inputEmail.equals(email) && inputName.equals(userName2) && inputPassword.equals(password2) && inputChk.equals(chkAgree)) {
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
        }

        if (!inputChk) {
            Toast.makeText(applicationContext, "이용약관에 동의해 주세요.", Toast.LENGTH_SHORT).show()

            return
        } else if (inputEmail.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            email.requestFocus()
            return
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            Toast.makeText(applicationContext, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            email.requestFocus()
            return
        }
        else if (inputName.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            userName2.requestFocus()
            return
        }
        else if (inputPassword.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            password2.requestFocus()

            return
        }
        else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", inputPassword)) {
            Toast.makeText(applicationContext, "비밀번호는 영어,숫자,특수문자 포함 8-20자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            password2.requestFocus()

            return
        }
        else if(checkpoint == 0){
            Toast.makeText(applicationContext, "중복확인을 해주세요!", Toast.LENGTH_SHORT).show()
        }


        // SQL LITE 사용하여 검증로직 추가
        // Toast.makeText(activity, "이미 가입된 회원정보 입니다.", Toast.LENGTH_SHORT).show()
        else {

            var checknum: TextView? = findViewById(R.id.checked_signup)

            println(checknum?.text.toString())

            if(checknum?.text.toString() == "0" ) {

                Toast.makeText(applicationContext, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()

                INSERTUSER(this, this, this).execute()
                INSERTUSER_plus0(this).execute()

                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main_frame, Frag_Login()).commit()
            }

            else {
                Toast.makeText(applicationContext, "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show()

                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main_frame, Frag_Register()).commit()

                checkpoint = 0
            }


            // 프래그먼트에서 액티비티 이동시 사용
            // val intent = Intent(activity as LoginRegister, Main::class.java)
            // startActivity(intent)
        }
    }

    fun checkedUser(v: View){

        var checkId: TextView? = findViewById(R.id.userName2)
        var checkId2 = checkId!!.text.trim().toString()
        var doubleCheck: TextView? = findViewById(R.id.check_bt)

        userName2 = findViewById(R.id.userName2)

        val inputName = userName2.text.trim().toString()

        if(inputName.isNullOrEmpty()){
            Toast.makeText(applicationContext, "아이디를 입력 해주세요", Toast.LENGTH_SHORT).show()
            userName2.requestFocus()
            return
        }
        else{

            CHECKUSER(this).execute("$checkId2")

            checkpoint = 1
            userName2.setEnabled(false)
            doubleCheck?.setText("Click Sign Up")
        }
    }

    fun receiveData(rid: String){

        Log.d("Main", "user_id =  $rid 입니다.")

        val intent = Intent(this, Main::class.java)
        intent.putExtra("user_id", "$rid")
        startActivity(intent)
        finish()
    }


}

