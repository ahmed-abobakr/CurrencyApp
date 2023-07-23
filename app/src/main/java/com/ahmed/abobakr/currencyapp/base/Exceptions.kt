package com.ahmed.abobakr.currencyapp.base



class ApiException(override val message: String) : Exception(message)

class NetworkException(message: String): Exception(message)

class AuthException(override val message: String): Exception(message)
