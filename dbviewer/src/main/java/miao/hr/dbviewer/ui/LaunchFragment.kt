package miao.hr.dbviewer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_launch.*
import kotlinx.android.synthetic.main.fragment_launch.view.*
import miao.hr.dbviewer.R

class LaunchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(R.layout.fragment_launch, container, false)
        initView(contentView)
        return contentView
    }

    private fun initView(contentView: View) {
        contentView.btn_navi.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_launchFragment_to_tableDetailFragment)
        }
    }

    companion object {

    }
}
