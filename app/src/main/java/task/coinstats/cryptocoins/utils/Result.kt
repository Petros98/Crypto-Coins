package task.coinstats.cryptocoins.utils

sealed class Result<out S> {
    data class Success<S>(val data: S?) : Result<S>()

    data class Error<E>(val errors: CallException<E>) : Result<Nothing>()
}