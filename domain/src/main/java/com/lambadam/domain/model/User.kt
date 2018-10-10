package com.lambadam.domain.model

import com.sun.jndi.toolkit.url.Uri
import java.net.URI

data class User(val id: String,
                val userName: String,
                val name: String,
                val lastName: String,
                val email: String,
                val profileUrl: String)