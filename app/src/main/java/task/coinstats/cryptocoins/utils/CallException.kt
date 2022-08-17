package task.coinstats.cryptocoins.utils

data class CallException<ErrorBody>(
    val errorCode: Int,
    val errorMessage: String? = null,
    val errorBody: ErrorBody? = null
)