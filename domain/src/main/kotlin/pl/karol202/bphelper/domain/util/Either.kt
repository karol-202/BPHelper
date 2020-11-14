package pl.karol202.bphelper.domain.util

sealed class Either<out A, out B>
{
	data class Left<A>(val value: A) : Either<A, Nothing>()
	{
		override fun <NA> mapLeft(transform: (A) -> NA) = Left(transform(value))

		override fun <NB> mapRight(transform: (Nothing) -> NB) = this
	}

	data class Right<B>(val value: B) : Either<Nothing, B>()
	{
		override fun <NA> mapLeft(transform: (Nothing) -> NA) = this

		override fun <NB> mapRight(transform: (B) -> NB) = Right(transform(value))
	}

	abstract fun <NA> mapLeft(transform: (A) -> NA): Either<NA, B>

	abstract fun <NB> mapRight(transform: (B) -> NB): Either<A, NB>
}

fun <A> A.left() = Either.Left(this)
fun <B> B.right() = Either.Right(this)
