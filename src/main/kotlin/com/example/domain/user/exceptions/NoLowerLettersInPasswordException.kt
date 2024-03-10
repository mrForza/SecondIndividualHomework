package com.example.domain.user.exceptions

import com.example.domain.common.BaseException

class NoLowerLettersInPasswordException(message: String) : BaseException(message)