package pl.karol202.bphelper.domain.util

sealed class Either<out A, out B>
{
	data class Left<A>(val value: A) : Either<A, Nothing>()
	{
		override fun <NA> mapLeft(transform: (A) -> NA) = Left(transform(value))

		override fun <NB> mapRight(transform: (Nothing) -> NB) = this

		override fun <R> fold(ifLeft: (A) -> R, ifRight: (Nothing) -> R) = ifLeft(value)

		override fun leftOrNull() = value

		override fun rightOrNull() = null
	}

	data class Right<B>(val value: B) : Either<Nothing, B>()
	{
		override fun <NA> mapLeft(transform: (Nothing) -> NA) = this

		override fun <NB> mapRight(transform: (B) -> NB) = Right(transform(value))

		override fun <R> fold(ifLeft: (Nothing) -> R, ifRight: (B) -> R) = ifRight(value)

		override fun leftOrNull() = null

		override fun rightOrNull() = value
	}

	abstract fun <NA> mapLeft(transform: (A) -> NA): Either<NA, B>

	abstract fun <NB> mapRight(transform: (B) -> NB): Either<A, NB>

	abstract fun <R> fold(ifLeft: (A) -> R, ifRight: (B) -> R): R

	abstract fun leftOrNull(): A?

	abstract fun rightOrNull(): B?
}

fun <A> A.left() = Either.Left(this)
fun <B> B.right() = Either.Right(this)
