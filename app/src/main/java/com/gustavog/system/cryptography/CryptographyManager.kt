package com.gustavog.system.cryptography

import com.gustavog.model.domain.CipherTextWrapper
import javax.crypto.Cipher

interface CryptographyManager {

    fun getInitializedCipherForEncryption(keyName: String): Cipher

    fun getInitializedCipherForDecryption(keyName: String, initializationVector: ByteArray): Cipher

    fun encryptData(plaintext: String, cipher: Cipher): CipherTextWrapper

    fun decryptData(ciphertext: ByteArray, cipher: Cipher): String

    fun persistCiphertextWrapperToSharedPrefs(ciphertextWrapper: CipherTextWrapper?)

    fun getCiphertextWrapperFromSharedPrefs(): CipherTextWrapper?

}
