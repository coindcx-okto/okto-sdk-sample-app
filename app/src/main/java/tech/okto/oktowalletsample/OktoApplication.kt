package tech.okto.oktowalletsample

import android.app.Application
import tech.okto.oktowallet.OktoWallet

class OktoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OktoWallet.init(this,"<API-KEY>");

    }
}