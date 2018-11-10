package erfolgi.com.football_match.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import erfolgi.com.football_match.fragments.TeamDescFragment
import erfolgi.com.football_match.fragments.TeamPlayerFragment

class TeamTabbedDetailAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return TeamDescFragment()
            }
            1 -> {
                return TeamPlayerFragment()
            }
        }
        return null
    }

    override fun getCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Team Overview"

            1 -> return "Team Player"
        }
        return null
    }

    companion object {
        private val FRAGMENT_COUNT = 2
    }
}