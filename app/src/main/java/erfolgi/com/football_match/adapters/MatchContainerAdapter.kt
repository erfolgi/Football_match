package erfolgi.com.football_match.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import erfolgi.com.football_match.fragments.LastMatchFragment
import erfolgi.com.football_match.fragments.NextMatchFragment

class MatchContainerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return LastMatchFragment()
            }
            1 -> {
                return NextMatchFragment()
            }
        }
        return null
    }

    override fun getCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Last Match"

            1 -> return "Next Match"
        }
        return null
    }

    companion object {
        private val FRAGMENT_COUNT = 2
    }
}