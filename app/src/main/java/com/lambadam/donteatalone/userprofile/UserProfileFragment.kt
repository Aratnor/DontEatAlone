package com.lambadam.donteatalone.userprofile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambadam.domain.model.User
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_user_profile

    private val viewModel by lazy{
        ViewModelProviders.of(this,
                UserProfileViewModelFactory.getInstance(activity!!.applicationContext))
                .get(UserProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewModel.user.observe(this, Observer(::showUser))
        viewModel.failure.observe(this, Observer { notify(root, R.string.something_went_wrong)})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUser(arguments?.getString(USER_PROFILE_BUNDLE).orEmpty())
    }

    private fun showUser(user: User){
        text_view.text = user.email
    }

    companion object {
        private const val TAG = "UserProfileFragment"
        private const val USER_PROFILE_BUNDLE = "USER_PROFILE_BUNDLE"

        fun forUser(userId: String): BaseFragment {
            val userProfileFragment = UserProfileFragment()
            val bundle = Bundle()
            bundle.putString(USER_PROFILE_BUNDLE, userId)
            userProfileFragment.arguments = bundle
            return userProfileFragment
        }
    }
}
