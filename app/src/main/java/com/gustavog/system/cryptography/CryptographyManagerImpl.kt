package com.gustavog.system.cryptography

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.google.gson.Gson
import com.gustavog.model.domain.CipherTextWrapper
import com.gustavog.model.storage.DataStorage
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptographyManagerImpl(private val dataStorage: DataStorage) :
    CryptographyManager {

    companion object {
        private const val KEY_SIZE = 256
        private const val GCM_PARAMETER_LENGTH = 128
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val UTF8 = "UTF-8"
        private const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    }

    override fun getInitializedCipherForEncryption(keyName: String): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    override fun getInitializedCipherForDecryption(
        keyName: String,
        initializationVector: ByteArray
    ): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(
            Cipher.DECRYPT_MODE,
            secretKey,
            GCMParameterSpec(GCM_PARAMETER_LENGTH, initializationVector)
        )
        return cipher
    }

    override fun encryptData(plaintext: String, cipher: Cipher): CipherTextWrapper {
        val cipherText = cipher.doFinal(plaintext.toByteArray(Charset.forName(UTF8)))
        return CipherTextWrapper(cipherText, cipher.iv)
    }

    override fun decryptData(ciphertext: ByteArray, cipher: Cipher): String =
        String(cipher.doFinal(ciphertext), Charset.forName(UTF8))

    private fun getCipher(): Cipher {
        val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"
        return Cipher.getInstance(transformation)
    }

    private fun getOrCreateSecretKey(keyName: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true)
        }

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)
        return keyGenerator.generateKey()
    }

    override fun persistCiphertextWrapperToSharedPrefs(ciphertextWrapper: CipherTextWrapper?) {
        dataStorage.saveFingerprintAuthKey(if (ciphertextWrapper != null) Gson().toJson(ciphertextWrapper) else null)
    }

    override fun getCiphertextWrapperFromSharedPrefs(): CipherTextWrapper? {
        val json = dataStorage.getFingerprintAuthKey()
        return Gson().fromJson(json, CipherTextWrapper::class.java)
    }
}
