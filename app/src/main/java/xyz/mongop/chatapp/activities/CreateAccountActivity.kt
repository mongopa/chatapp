package xyz.mongop.chatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import xyz.mongop.chatapp.R

class CreateAccountActivity : AppCompatActivity() {
     var mAuth: FirebaseAuth? = null
     var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        mAuth = FirebaseAuth.getInstance()

        accountCreateActBtn.setOnClickListener {
            var email = accountEmailEt.text.toString().trim()
            var password = accountPasswordEt.text.toString().trim()
            var displayName = accountDisplayNameEt.text.toString().trim()

            if (!TextUtils.isEmpty(email) and !TextUtils.isEmpty(password) and  !TextUtils.isEmpty(displayName)) {
                createAccount(email, password, displayName)
            }else {
                Toast.makeText(this, "入力してください", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun createAccount( email: String, password: String, displayName: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->

            if (task.isSuccessful) {
                var currentUser = mAuth!!.currentUser
                var userId = currentUser!!.uid

                mDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

                /*
                            Users
                                - 77rkdkekkdkd
                                   - Paulo
                                   - "hello there"
                                   - "image url.."
                         */

                var userObject = HashMap<String, String>()
                userObject.put("display_name", displayName)
                userObject.put("status", "Hello there....")
                userObject.put("image", "default")
                userObject.put("thumb_image", "default")

                mDatabase!!.setValue(userObject).addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
//                               Toast.makeText(this, "ユーザー登録が完了しました", Toast.LENGTH_LONG).show()
                        var dashboardIntent = Intent(this, DashboardActivity::class.java)
                        dashboardIntent.putExtra("name", displayName)
                        startActivity(dashboardIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "ユーザー登録に失敗しました", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
            }
        }
    }
}
