package com.lambadam.data.manager

import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.lambadam.domain.auth.AuthManager
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.model.None
import com.lambadam.domain.model.Result
import com.lambadam.domain.model.User

class AuthManagerImp : AuthManager {

    private lateinit var mAuth: FirebaseAuth

    private  var isSuccessful : Boolean = false

    private var error : String = ""

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
        return facebookLogin(token)

    }




    fun facebookLogin(token: String): Result<Exception,None> {

        mAuth = FirebaseAuth.getInstance()


        var credential = FacebookAuthProvider.getCredential(token)


        mAuth.signInWithCredential(credential).addOnCompleteListener() { task ->
            if(task.isSuccessful) {
                //Sign in success
                isSuccessful = task.isSuccessful
            }
            else {
                error = task.exception.toString()
            }
        }
        if(isSuccessful) {
            return Result.buildValue { None() }
        }
        else {
            return Result.buildError(Exception(error))
        }
    }

}