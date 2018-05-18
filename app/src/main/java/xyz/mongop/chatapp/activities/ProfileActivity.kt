package xyz.mongop.chatapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import xyz.mongop.chatapp.R

class ProfileActivity : AppCompatActivity() {

    var mUsersDatabase : DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null
    var userId :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar!!.title = "プロフィール"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(intent.extras != null){
            userId = intent.extras.get("userId").toString()

            mCurrentUser = FirebaseAuth.getInstance().currentUser
            mUsersDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

            setUpProfile()
        }


    }

    private fun setUpProfile() {
        mUsersDatabase!!.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot?) {

                var displayName = dataSnapshot!!.child("display_name").value.toString()
                var status = dataSnapshot!!.child("status").value.toString()
                var image = dataSnapshot!!.child("image").value.toString()

                profileName.text = displayName
                profileStatus.text = status
                Picasso.with(this@ProfileActivity)
                        .load(image)
                        .placeholder(R.drawable.profile_img)
                        .into(profilePicture)

            }

            override fun onCancelled(databaseError: DatabaseError?) {

            }

        })
    }
}
