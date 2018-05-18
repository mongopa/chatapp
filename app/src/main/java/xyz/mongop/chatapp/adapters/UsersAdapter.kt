package xyz.mongop.chatapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import xyz.mongop.chatapp.R
import xyz.mongop.chatapp.activities.ChatActivity
import xyz.mongop.chatapp.activities.ProfileActivity
import xyz.mongop.chatapp.models.Users

/**
 * Created by Owner on 2017/10/21.
 */
class UsersAdapter(databaseQuery:DatabaseReference, var context:Context)
    :FirebaseRecyclerAdapter<Users, UsersAdapter.ViewHolder>(
        Users::class.java,
        R.layout.users_row,
        UsersAdapter.ViewHolder::class.java,
        databaseQuery
){
    override fun populateViewHolder(viewHolder: UsersAdapter.ViewHolder?, user: Users?, position: Int) {
        var userId = getRef(position).key //FirebaseのキーIDユーザーIDにあてる
        viewHolder!!.bindView(user!!,context)

        viewHolder.itemView.setOnClickListener {
            //プロフィール閲覧とメッセージを送るダイアログ
            var options = arrayOf("Open Profile","Send Message")
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Select Options")
            builder.setItems(options, DialogInterface.OnClickListener { dialogInterface, i ->
                var userName = viewHolder.userNameTxt
                var userStat = viewHolder.userStatusTxt
                var profilePic = viewHolder.userProfilePicLink

                if(i == 0){
                    //プロフィールを開く

                    var profileIntent = Intent(context, ProfileActivity::class.java)
                    profileIntent.putExtra("userId",userId)
                    context.startActivity(profileIntent)
                }else{
                    //メッセージを送る/チャットアクティビティへ
                    var chatIntent = Intent(context, ChatActivity::class.java)
                    chatIntent.putExtra("userId",userId)
                    chatIntent.putExtra("name",userName)
                    chatIntent.putExtra("status",userStat)
                    chatIntent.putExtra("profile",profilePic)
                    context.startActivity(chatIntent)

                }
            })

            builder.show()
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var userNameTxt:String?  =null
        var userStatusTxt:String? = null
        var userProfilePicLink:String? = null

        fun bindView(user:Users, context:Context){
            var userName = itemView.findViewById<TextView>(R.id.userName)
            var userStatus = itemView.findViewById<TextView>(R.id.userStatus)
            var userProfilePic = itemView.findViewById<CircleImageView>(R.id.userProfile)

            //文字をインテントで渡す
            userNameTxt = user.display_name
            userStatusTxt = user.user_status
            userProfilePicLink = user.thumb_image

            userName.text = user.display_name
            userStatus.text = user.user_status

            Picasso.with(context).load(userProfilePicLink)
                    .placeholder(R.drawable.profile_img).into(userProfilePic)
        }

    }
}