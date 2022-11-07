package org.apps.library.user.config

import org.springframework.stereotype.Component
import org.springframework.boot.CommandLineRunner
import org.apps.library.user.user.User
import org.apps.library.user.user.UserRepository

@Component
class UserDataLoader(
    private val userRepository: UserRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val users = listOf<User>(User(name="Paco"), User(name="Vicente"), User(name="Valeria"))
        userRepository.saveAll(users)
    }
}
