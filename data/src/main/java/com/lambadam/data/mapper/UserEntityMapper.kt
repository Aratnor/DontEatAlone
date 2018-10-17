package com.lambadam.data.mapper

import com.lambadam.data.entity.UserEntity
import com.lambadam.domain.model.User

class UserEntityMapper: EntityMapper<User, UserEntity> {

    override fun mapFromEntity(entity: UserEntity): User {
        return User(entity.id,
                entity.userName,
                entity.displayName,
                entity.email,
                entity.profileUrl)
    }

    override fun mapToEntity(domain: User): UserEntity {
        return UserEntity(domain.id,
                domain.userName,
                domain.displayName,
                domain.email,
                domain.profileUrl)
    }
}