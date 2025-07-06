package com.developersbreach.composeactors.domain.core

class SignInException(val key: UserMessageKey) : Exception(key.name)
