package erfolgi.com.football_match.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import erfolgi.com.football_match.R
import erfolgi.com.football_match.activities.TabbedActivity
import erfolgi.com.football_match.adapters.MatchContainerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchContainerFragment : Fragment() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_match_container, container, false)
        Log.d("debugg","contfrag")
        tabLayout = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.view_pager)
        val con = TabbedActivity

        viewPager.adapter = MatchContainerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        return view

    }


}
