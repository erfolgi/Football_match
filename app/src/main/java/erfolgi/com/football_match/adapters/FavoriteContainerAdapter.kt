package erfolgi.com.football_match.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import erfolgi.com.football_match.fragments.FavoriteMatchFragment
import erfolgi.com.football_match.fragments.FavoritePlayerFragment
import erfolgi.com.football_match.fragments.FavoriteTeamFragment
import erfolgi.com.football_match.fragments.NextMatchFragment

class FavoriteContainerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return FavoriteMatchFragment()
            }
            1 -> {
                return FavoriteTeamFragment()
            }
            2 -> {
                return FavoritePlayerFragment()
            }
        }
        return null
    }

    override fun getCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Match"

            1 -> return "Team"

            2 -> return "Player"
        }
        return null
    }

    companion object {
        private val FRAGMENT_COUNT = 3
    }
}