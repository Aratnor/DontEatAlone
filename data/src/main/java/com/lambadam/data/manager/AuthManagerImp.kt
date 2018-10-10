package com.lambadam.data.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.lambadam.domain.auth.AuthManager
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User

class AuthManagerImp : AuthManager {

    private lateinit var mAuth: FirebaseAuth



    override fun getCurrentUser(): Result<Exception, User> {

        mAuth = FirebaseAuth.getInstance()

        var currentUser = mAuth.currentUser

        if(currentUser == null)
            return Result.buildError(Exception("No user logged in!"))
        else {
            var user = User(currentUser.uid,"", currentUser.displayName!!, currentUser.displayName!!, currentUser.email!!,currentUser.photoUrl.toString())

            return Result.buildValue {  user }

        }


     }

    override fun logout(): Result<Exception, None> {

        mAuth = FirebaseAuth.getInstance()

        try {
            mAuth.signOut()
        }
        catch(e: FirebaseAuthInvalidUserException) {
            return Result.buildError(e);
        }

           return Result.buildValue {
               None()
           }
    }

    override fun login(type: AuthType, token: String): Result<Exception, None> {

    }

}