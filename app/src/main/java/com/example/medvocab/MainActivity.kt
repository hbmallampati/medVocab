package com.example.medvocab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.MainThread
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.medvocab.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    companion object IntentStrings {
        const val callingActivityKey = "callingActivity"
    }

    //Firebase auth
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK)
        {
            viewModel.setSIgnInStatus(1)

            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.d(javaClass.simpleName, "Successfully signed in")

            if (response != null)
            {
                if(response.isNewUser)
                {
                    //viewModel.createNewUser()
                    viewModel.createNewUser2()
                }
            }
            else
            {
                //val temp = viewModel.getUserDocument(viewModel.getUid())
                //Log.d(javaClass.simpleName, "temp from fbase doc : $temp")
                Toast.makeText(this, "Something is wrong with firebase firestore", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Log.d(javaClass.simpleName, "Sign in failed")
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Toast.makeText(this, "Sign in failed !!!", Toast.LENGTH_LONG).show()
        }
    }

    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarMain.toolbar.title = R.string.app_name.toString()
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        navView = binding.navView

        //val navController = findNavController(R.id.nav_host_fragment_content_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_study, R.id.nav_settings, R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Authentication [signing in, registering new users]
        if(FirebaseAuth.getInstance().currentUser == null)
        {
            viewModel.setSIgnInStatus(0)
            createSignInIntent()
        }
        else
            viewModel.setSIgnInStatus(1)


        //After type selection --> Go to start learning activity
        viewModel.observeTypeSelection().observe(this, Observer {
            if(it != -1)
            {
                val startLearningIntent = Intent(this, VocabBuildActivity::class.java)
                startLearningIntent.putExtra(callingActivityKey, it)
                startActivity(startLearningIntent)
            }
        })

//        var menuItem = navView.menu.findItem(R.id.action_profile)
//        //observe sign in/ sign out status and update accordingly menu item
//        viewModel.getSignInStatus().observe(this, Observer {
//            if(it == 0)
//                menuItem.title = "Sign in"
//            else
//                menuItem.title = "Sign out"
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
         if(viewModel.getSignInStatus2() == 0)
             menu.findItem(R.id.action_profile).title = "Sign in"

        viewModel.getSignInStatus().observe(this, Observer {
            if(it == 0)
                menu.findItem(R.id.action_profile).title = "Sign in"
            else
                menu.findItem(R.id.action_profile).title = "Sign out"
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_profile)
        {
            Log.d(javaClass.simpleName, "item selected")
            if(FirebaseAuth.getInstance().currentUser == null)
            {
                createSignInIntent()
            }
            else
            {
                Log.d(javaClass.simpleName, "user not null")
                viewModel.signOut(this)
                Toast.makeText(this, "Signed out successfully", Toast.LENGTH_LONG).show()
                viewModel.setSIgnInStatus(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}