package com.qontak.assignment.authentication

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qontak.assignment.Constants
import com.qontak.assignment.R

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*

interface LoginView {
    fun finishActivity(sessionId: String)
}

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        loginPresenter = LoginPresenter(this, LoginInteractor())

        loginButton.setOnClickListener {
            if (loginUsername.text.isNotEmpty() && loginPassword.text.isNotEmpty()) {
                loginPresenter.login(
                    loginUsername.text.toString(),
                    loginPassword.text.toString()
                )
            }
        }
    }

    override fun finishActivity(sessionId: String) {
        val editor = getSharedPreferences(Constants.PREF_NAME_AUTH, Context.MODE_PRIVATE).edit()
        editor.putString(Constants.PREF_KEY_SESSION_ID, sessionId)
        editor.apply()
        runOnUiThread {
            Toast.makeText(this, "Log in succeed", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    override fun onDestroy() {
        loginPresenter.onDestroy()
        super.onDestroy()
    }
}
