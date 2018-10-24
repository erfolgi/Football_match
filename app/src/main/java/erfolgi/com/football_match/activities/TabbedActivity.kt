package erfolgi.com.football_match.activities



import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout

import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.TabbedAdapter
import android.support.v4.view.ViewPager

class TabbedActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    internal var con: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)
        supportActionBar!!.title = "Match List"


        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.view_pager)

        viewPager.adapter = TabbedAdapter(supportFragmentManager, this)
        tabLayout.setupWithViewPager(viewPager)
    }
}
