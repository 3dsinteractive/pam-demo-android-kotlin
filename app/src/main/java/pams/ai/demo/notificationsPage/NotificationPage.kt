package pams.ai.demo.notificationsPage

import ai.pams.android.kotlin.Pam
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import models.AppData
import pams.ai.demo.LoginPage
import pams.ai.demo.databinding.ActivityNotificationPageBinding
import webservices.MockAPI

class NotificationPage : AppCompatActivity() {

    private var binding: ActivityNotificationPageBinding? = null
    private var adapter: NotificationListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationPageBinding.inflate(layoutInflater)
        binding?.let {
            setContentView(it.root)
        }

        registerNotificationView()
        registerUserButton()
        registerLoginButton()
        registerLogoutButton()
    }

    override fun onResume() {
        super.onResume()

        fetchNotifications()
    }

    private fun registerNotificationView() {
        adapter = NotificationListAdapter()

        val layoutManager =
            LinearLayoutManager(this@NotificationPage, LinearLayoutManager.VERTICAL, false)
        binding?.listView?.layoutManager = layoutManager
        binding?.listView?.adapter = adapter
    }


    private fun registerUserButton() {
        binding?.let {
            it.btnUser.setOnClickListener {
                binding?.let { b ->
                    if (AppData.getUser() == null) {
                        if (b.btnLogin.visibility == View.INVISIBLE) {
                            b.btnLogin.visibility = View.VISIBLE
                        } else {
                            b.btnLogin.visibility = View.INVISIBLE
                        }
                    } else {
                        if (b.btnLogout.visibility == View.INVISIBLE) {
                            b.btnLogout.visibility = View.VISIBLE
                        } else {
                            b.btnLogout.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun registerLoginButton() {
        binding?.let {
            it.btnLogin.setOnClickListener {
                val intent = Intent(this, LoginPage::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                startActivity(intent)
                this.finish()
            }
        }
    }

    private fun registerLogoutButton() {
        binding?.btnLogout?.setOnClickListener {
            Pam.userLogout()

            val intent = Intent(this, LoginPage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            startActivity(intent)
            this.finish()
        }
    }

    private fun fetchNotifications() {
        Pam.fetchNotificationHistory { notiList ->
            notiList?.let{
                adapter?.setNotifications(notifications = it)
            }
        }
    }
}