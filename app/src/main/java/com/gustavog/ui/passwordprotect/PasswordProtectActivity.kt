package com.gustavog.ui.passwordprotect

import android.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.system.cryptography.BiometricPromptUtils
import com.gustavog.system.cryptography.CryptographyManager
import com.gustavog.system.extensions.bindLiveData
import kotlinx.android.synthetic.main.activity_password_protect.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PasswordProtectActivity : BaseActivity<PasswordProtectViewModel, PasswordProtectViewModel.State>() {

    override val model: PasswordProtectViewModel by viewModel()
    private val cryptographyManager: CryptographyManager by inject()

    override fun layoutResource() = R.layout.activity_password_protect

    override fun setupViews() {
        button_lock.setOnClickListener {
            model.savePin()
            val canAuthenticate =
            BiometricManager.from(applicationContext).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.use_fingerprint))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        showBiometricPromptForEncryption()
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    .show()
            } else {
                finish()
            }
        }
    }

    override fun setupBindings() {
        edit_text_text_input.bindLiveData(this, model.getPassword()) {
            button_lock.isEnabled = it.isNotBlank()
            model.setPassword(it)
        }
    }

    override fun onStateChanged(state: PasswordProtectViewModel.State) = Unit

    private fun showBiometricPromptForEncryption() {
        val cipher = cryptographyManager.getInitializedCipherForEncryption(getString(R.string.secret_key_name))
        val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(this, ::encryptAndStoreServerToken) { }

        val promptInfo =
            BiometricPromptUtils.createPromptInfo(
                getString(R.string.prompt_info_title),
                getString(R.string.prompt_info_confirm_password_subtitle),
                getString(R.string.prompt_info_use_app_password)
            )

        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {
            model.getPassword().value?.let { confirmPassword ->
                    val encryptedServerTokenWrapper =
                        cryptographyManager.encryptData(confirmPassword, this)
                    cryptographyManager.persistCiphertextWrapperToSharedPrefs(encryptedServerTokenWrapper)
                    finish()
            }
        }
    }

}
