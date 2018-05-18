package xyz.mongop.chatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_status.*
import xyz.mongop.chatapp.R

class StatusActivity : AppCompatActivity() {
    var mDatabase : DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar!!.title = "ステータス"

        if (intent.extras != null){
            var oldStatus = intent.extras.get("status")
            statusUpdateEt.setText(oldStatus.toString())
        }
        if (intent.extras.equals(null)){
            statusUpdateEt.setText("Enter Your New Status")
        }
        statusUpdateBtn.setOnClickListener {
            //todo ここら辺使ってアカウントの作成できないか。
            //Authだけ作ってその後データベースにつなげる
            mCurrentUser= FirebaseAuth.getInstance().currentUser
            var userId = mCurrentUser!!.uid

            mDatabase  = FirebaseDatabase.getInstance().reference
                    .child("Users").child(userId)

            var status = statusUpdateEt.text.toString().trim()

            mDatabase!!.child("status").setValue(status).addOnCompleteListener {
                 task: Task<Void> ->
                if (task.isSuccessful){
                    Toast.makeText(this, "ステータスの更新が完了しました",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,SettingsActivity::class.java))
                }else{
                    Toast.makeText(this, "ステータスの更新に失敗しました",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
