package pl.akai.fillist.security.sso.exceptions

class InvalidGrantException(description: String) : Exception("Invalid grant: $description")
