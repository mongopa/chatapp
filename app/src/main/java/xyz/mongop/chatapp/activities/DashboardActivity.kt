package xyz.mongop.chatapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import xyz.mongop.chatapp.R
import xyz.mongop.chatapp.adapters.SectionPagerAdapter

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var sectionAdapter:SectionPagerAdapter? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar!!.title = "Dashboard"

        sectionAdapter = SectionPagerAdapter(supportFragmentManager)
        dashViewPagerId.adapter = sectionAdapter
        mainTabs.setupWithViewPager(dashViewPagerId)
        //現在のフラグメントの文字が緑になる
        mainTabs.setTabTextColors(Color.WHITE,Color.GREEN)

//        ログインしたユーザーネームをToastしている。
//        if (intent.extras != null){
//            var username = intent.extras.get("name")
//
//            Toast.makeText(this, username.toString(),Toast.LENGTH_LONG).show()
//        }
        }

    //MenuのXMLファイル
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)

        if (item != null){
            if (item.itemId == R.id.logoutId){
                //ユーザーのログアウト
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }

            if (item.itemId == R.id.settingsId){
                //設定画面へ移動
                startActivity(Intent(this,SettingsActivity::class.java))
            }
        }
        return true

    }
}
