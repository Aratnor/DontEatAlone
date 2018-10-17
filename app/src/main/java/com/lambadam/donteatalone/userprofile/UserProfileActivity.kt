package com.lambadam.donteatalone.userprofile

import android.content.Context
import android.content.Intent
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.base.BaseActivity
import com.lambadam.donteatalone.base.BaseFragment

class UserProfileActivity : BaseActivity(){

    override val layoutId: Int = R.layout.activity_user_profile
    override val fragmentContainerId: Int = R.id.user_profile_container

    override fun fragment(): BaseFragment = UserProfileFragment.forUser(intent.getStringExtra(USER_PROFILE_INTENT))

    companion object {
        const val USER_PROFILE_INTENT = "USER_PROFILE_INTENT"

        fun intent(context: Context, userId: String): Intent{
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(USER_PROFILE_INTENT, userId)
            return intent
        }
    }
}
