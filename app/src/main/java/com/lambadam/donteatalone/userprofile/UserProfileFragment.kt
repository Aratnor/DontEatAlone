package com.lambadam.donteatalone.userprofile

import android.os.Bundle
import com.lambadam.data.exception.FirestoreError
import com.lambadam.domain.exception.Error
import com.lambadam.domain.model.User
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.base.BaseFragment
import com.lambadam.donteatalone.extension.appContext
import com.lambadam.donteatalone.extension.loadFromUrl
import com.lambadam.donteatalone.extension.observe
import com.lambadam.donteatalone.extension.viewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_user_profile

    private lateinit var viewModel: UserProfileViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = viewModel(UserProfileViewModelFactory.getInstance(appContext)) {
            observe(user, ::showUser)
            observe(failure, ::handleFailure)
        }

        viewModel.loadUser(arguments?.getString(USER_PROFILE_BUNDLE).orEmpty())
    }

    private fun showUser(user: User?){
        user?.run {
            text_name.text = displayName
            profile_image.loadFromUrl(appContext, profileUrl)
        }
    }

    private fun handleFailure(error: Error){
        when(error){
            is FirestoreError.NotFound -> notify(root, R.string.not_found)
            is FirestoreError.Unavailable, Error.Unknown  -> notify(root, R.string.something_went_wrong)
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
