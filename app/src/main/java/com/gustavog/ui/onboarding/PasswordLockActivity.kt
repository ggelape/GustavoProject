package com.gustavog.ui.onboarding

import android.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.gustavog.R
import com.gustavog.model.domain.CipherTextWrapper
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.system.cryptography.BiometricPromptUtils
import com.gustavog.system.cryptography.CryptographyManager
import com.gustavog.system.extensions.bindLiveData
import kotlinx.android.synthetic.main.activity_password_lock.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PasswordLockActivity : BaseActivity<PasswordLockViewModel, PasswordLockViewModel.State>() {

    override val model: PasswordLockViewModel by viewModel()
    private val cryptographyManager: CryptographyManager by inject()
    private var ciphertextWrapper: CipherTextWrapper? = null

    override fun layoutResource(): Int = R.layout.activity_password_lock

    override fun setupViews() {
        ciphertextWrapper = cryptographyManager.getCiphertextWrapperFromSharedPrefs()
        button_unlock.setOnClickListener {
            model.unlock()
        }

        val canAuthenticate = BiometricManager.from(applicationContext)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS && ciphertextWrapper != null) {
            showBiometricPromptForDecryption()
        }
    }

    override fun setupBindings() {
        edit_text_text_input.bindLiveData(this, model.getPassword()) {
            button_unlock.isEnabled = it.isNotBlank()
            model.setPassword(it)
        }
    }

    override fun onStateChanged(state: PasswordLockViewModel.State) {

        when (state) {
            PasswordLockViewModel.State.PASSWORD_CORRECT -> navigation.goToMain(this, true)
            PasswordLockViewModel.State.PASSWORD_INCORRECT -> {
                AlertDialog.Builder(this)
                    .setTitle("Incorrect Password")
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

    }

    private fun showBiometricPromptForDecryption() {
        ciphertextWrapper?.let { textWrapper ->
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                getString(R.string.secret_key_name), textWrapper.initializationVector
            )
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this, ::decryptServerTokenFromStorage) {

                }

            val promptInfo = BiometricPromptUtils.createPromptInfo(
                getString(R.string.prompt_info_title),
                getString(R.string.prompt_info_sign_in_subtitle),
                getString(R.string.prompt_info_use_app_password)
            )
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    private fun decryptServerTokenFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
        ciphertextWrapper?.let { textWrapper ->
            authResult.cryptoObject?.cipher?.let {
                val plaintext =
                    cryptographyManager.decryptData(textWrapper.ciphertext, it)
                model.setPassword(plaintext)
                model.unlock()
            }
        }
    }

}
