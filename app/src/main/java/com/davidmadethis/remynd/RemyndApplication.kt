package com.davidmadethis.remynd

import android.content.Context
import android.support.multidex.MultiDexApplication
import android.support.multidex.MultiDex
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.FirebaseFirestore







public class RemyndApplication: MultiDexApplication() {

    protected override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        db.setFirestoreSettings(settings)
    }
}
