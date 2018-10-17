package com.lambadam.donteatalone.userprofile

import android.os.Bundle
import android.view.View
import com.lambadam.domain.model.User
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.base.BaseFragment
import com.lambadam.donteatalone.extension.appContext
import com.lambadam.donteatalone.extension.observe
import com.lambadam.donteatalone.extension.viewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_user_profile

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        viewModel = viewModel(UserProfileViewModelFactory.getInstance(appContext)){
            observe(user, ::showUser)
            observe(failure) {notify(root, R.string.something_went_wrong)}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUser(arguments?.getString(USER_PROFILE_BUNDLE).orEmpty())
    }

    private fun showUser(user: User?){
        user?.email?.let {
            text_view.text = it
        }
    }

    companion object {
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
