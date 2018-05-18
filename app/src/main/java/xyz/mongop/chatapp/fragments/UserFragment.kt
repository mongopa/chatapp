package xyz.mongop.chatapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_user.*

import xyz.mongop.chatapp.R
import xyz.mongop.chatapp.adapters.UsersAdapter


/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {
    var mUserDatabase:DatabaseReference? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        mUserDatabase = FirebaseDatabase.getInstance().reference.child("Users")
        userRecyclerViewId.setHasFixedSize(true)

        userRecyclerViewId.layoutManager = linearLayoutManager
        userRecyclerViewId.adapter = UsersAdapter(mUserDatabase!!,context)
    }

}// Required empty public constructor
