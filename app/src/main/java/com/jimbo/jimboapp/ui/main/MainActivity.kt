package com.jimbo.jimboapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.jimbo.jimboapp.R
import com.jimbo.jimboapp.databinding.ActivityMainBinding
import com.jimbo.jimboapp.ui.login.AuthRequestStatus
import com.jimbo.jimboapp.ui.main.adapter.ClientsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.clientsRecyclerView.layoutManager = layoutManager

        binding.clientsRecyclerView.addItemDecoration(
            DividerItemDecoration(this, layoutManager.orientation)
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainState.collect {
                    withContext(Dispatchers.Main) {
                        binding.clientsRecyclerView.isVisible = !it.loading
                        binding.clientsLoadingIndicator.isVisible = it.loading
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.clientsResponseState.collect {
                    when (it) {
                        is ClientsResponseState.Success -> {
                            val adapter = ClientsAdapter(
                                context = this@MainActivity,
                                clients = it.clients
                            )
                            binding.clientsRecyclerView.adapter = adapter
                        }
                        is ClientsResponseState.Error -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Error Fetching Clients",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        val args = intent.extras
        if (args != null) {
            mainViewModel.getClients(args.getString("TOKEN", ""))
        }
    }
}