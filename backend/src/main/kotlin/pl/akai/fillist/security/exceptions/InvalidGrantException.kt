package pl.akai.fillist.security.exceptions

class InvalidGrantException(description: String) : Exception("Invalid grant: $description")
