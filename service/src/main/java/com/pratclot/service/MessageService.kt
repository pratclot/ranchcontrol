package com.pratclot.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val TAG = "MESSAGE_SERVICE"

class MessageService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, p0.toString().repeat(20))
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}
