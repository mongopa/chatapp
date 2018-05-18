package xyz.mongop.chatapp.adapters

//import android.app.FragmentManagerをimport android.support.v4.app.FragmentManagerに変えた
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import xyz.mongop.chatapp.fragments.ChatsFragment
import xyz.mongop.chatapp.fragments.UserFragment

/**
 * Created by Owner on 2017/10/17.
 */
class SectionPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return  UserFragment()
            }
            1->{
                return ChatsFragment()
            }
        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    //フラグメントのタイトル
    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            0->return "USERS"
            1->return "CHATS"
        }
        return null!!
    }
}