package erfolgi.com.football_match.activities



import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import erfolgi.com.football_match.R
import erfolgi.com.football_match.R.id.favorites
import erfolgi.com.football_match.R.id.team_
import erfolgi.com.football_match.fragments.FavoriteContainerFragment
import erfolgi.com.football_match.fragments.MatchContainerFragment
import erfolgi.com.football_match.fragments.NextMatchFragment
import erfolgi.com.football_match.fragments.TeamFragment
import kotlinx.android.synthetic.main.activity_tabbed.*


class TabbedActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    companion object {
        var con = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)
        supportActionBar?.title = "Match List"

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.match_ -> {
                    loadLastFragment(savedInstanceState)
                }
                team_ -> {
                    loadNextFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.match_
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        menuItem = menu


        return true
    }


    private fun loadLastFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchContainerFragment(), MatchContainerFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadNextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamFragment(), TeamFragment::class.java.simpleName)
                    .commit()
        }
    }
    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteContainerFragment(), FavoriteContainerFragment::class.java.simpleName)
                    .commit()

        }
    }
}
