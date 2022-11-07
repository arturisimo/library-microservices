package org.apps.library.user.user

import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long>
