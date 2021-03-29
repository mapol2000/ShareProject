package gteamproject.shere

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import gteamproject.shere.jdbc.CHECKUSER_login
import java.lang.Thread.sleep


class Frag_Login : Fragment() {


    lateinit var userName : EditText
    lateinit var password : EditText
    lateinit var btnSignin : Button
    lateinit var chkRemember : CheckBox
    lateinit var textForgot : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_login, container, false)

        super.onCreate(savedInstanceState)
        userName = view.findViewById(R.id.userName)
        password = view.findViewById(R.id.password)
        btnSignin = view.findViewById(R.id.btnSignin)
        chkRemember = view.findViewById(R.id.chkRemember)
        textForgot = view.findViewById(R.id.textForgot)

        var prefs = requireActivity().getPreferences(0);
        val editor : SharedPreferences.Editor = prefs.edit()

        if(prefs.getBoolean("chkRemember", true)){
            userName.setText(prefs.getString("userName", ""))
            chkRemember.isChecked = true
        }

        btnSignin.setOnClickListener {
            val inputName = userName.text.trim().toString()
            val inputPassword = password.text.trim().toString()

            if(inputName.isNullOrEmpty()) {
                Toast.makeText(activity, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                userName.requestFocus()
                return@setOnClickListener
            }
            else {
                if(inputPassword.isNullOrEmpty()){
                    Toast.makeText(activity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    password.requestFocus()
                    return@setOnClickListener
                }
                else {
                    // TODO: 체크박스 체크하고 로그인 성공 시 아이디 저장
                    if (chkRemember.isChecked) {
                        editor.putString("userName", inputName)
                        editor.putBoolean("chkRemember", chkRemember.isChecked)
                        editor.commit()
                    } else {
                        editor.putString("userName", "")
                        editor.putBoolean("chkRemember", false)
                        editor.commit()
                    }

                    CHECKUSER_login(this).execute("$inputName","$inputPassword")

                    var checknum: TextView? = view.findViewById(R.id.checked_login)
                    println(checknum?.text.toString())

                    userName.setEnabled(false)
                    password.setEnabled(false)
                    Handler().postDelayed(Runnable {

                        if(checknum?.text.toString() == "1" ) {

                            Toast.makeText(context, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                            userName.setEnabled(true)
                            password.setEnabled(true)

                            val mActivity = activity as LoginRegister
                            mActivity.receiveData(inputName)

                            /*val intent = Intent(activity as LoginRegister, Main::class.java)
                            startActivity(intent)
                            getActivity()?.finish()*/
                        }
                        else {
                            Toast.makeText(context, "아이디 또는 패스워드가 틀립니다.", Toast.LENGTH_SHORT).show()
                            userName.setEnabled(true)
                            password.setEnabled(true)
                        }
                    }, 1000)



                }
            }
        }

        return view
    }

}

