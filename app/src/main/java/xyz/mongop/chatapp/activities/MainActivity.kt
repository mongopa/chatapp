package xyz.mongop.chatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import xyz.mongop.chatapp.R

class MainActivity : AppCompatActivity() {
    var mAuth:FirebaseAuth? = null
    var user: FirebaseUser? = null
    var mAuthListener:FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            firebaseAuth:FirebaseAuth ->

                user = firebaseAuth.currentUser

            if (user != null){
                //DashboardActivityへいく。
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"サインインしていません", Toast.LENGTH_LONG).show()
            }
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        createActButton.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

    }

    //一旦サイトから離れて戻るときに再度ログインしなくて済む。
    override fun onStart() {
        super.onStart()

        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()

        if (mAuthListener != null){
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }
}
