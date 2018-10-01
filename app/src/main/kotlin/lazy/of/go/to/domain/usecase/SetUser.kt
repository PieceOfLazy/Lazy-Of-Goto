package lazy.of.go.to.domain.usecase

import io.reactivex.disposables.Disposable
import lazy.of.framework.library.util.Log
import lazy.of.go.to.auth.AuthUser
import lazy.of.go.to.domain.data.UserRepository
import lazy.of.go.to.domain.entity.UserEntity
import lazy.of.go.to.usecase.UseCase

class SetUser constructor(request: AuthUser, private val userRep: UserRepository): UseCase<AuthUser, UserEntity>(request) {

    private var observable : Disposable? = null

    override fun executeUseCase(request: AuthUser) {
        getLoadingFeature()?.loadingStart()
        observable = userRep.set(
                UserEntity(
                        request.isAnonymous,
                        request.providerId,
                        request.displayName ?: "",
                        request.email ?: "",
                        request.photoURL?.toString() ?: "")
        ).map {
            userRep.getUser()
        }.subscribe({ userEntity ->
            observable?.let {
                Log.d("SetUser", "AuthUser : subscribe onNext : ${it.isDisposed}")
            }
            success(userEntity)
            getLoadingFeature()?.loadingEnd()
        }, { throwable ->
            observable?.let {
                Log.d("SetUser", "AuthUser : subscribe onError : ${it.isDisposed}")
            }
            error(throwable)
            getLoadingFeature()?.loadingEnd()
        }, {
            observable?.let {
                Log.d("SetUser", "AuthUser : observable onComplete : ${it.isDisposed}")
            }
        })

        observable?.let {
            Log.d("SetUser", "AuthUser : observable isDisposed : ${it.isDisposed}")
        }


        Log.d("SetUser","AuthUser : uuid : ${request.uuid}")
        Log.d("SetUser","AuthUser : email : ${request.email}")
        Log.d("SetUser","AuthUser : emailVerified : ${request.emailVerified}")
        Log.d("SetUser","AuthUser : phoneNumber : ${request.phoneNumber}")
        Log.d("SetUser","AuthUser : displayName : ${request.displayName}")
        Log.d("SetUser","AuthUser : photoURL : ${request.photoURL}")
        Log.d("SetUser","AuthUser : isAnonymous : ${request.isAnonymous}")
        Log.d("SetUser","AuthUser : providerId : ${request.providerId}")
    }
}