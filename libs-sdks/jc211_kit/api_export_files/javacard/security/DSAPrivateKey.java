/*
* Copyright (c) 1999 Sun Microsystems, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Sun
* Microsystems, Inc. ("Confidential Information").  You shall not
* disclose such Confidential Information and shall use it only in
* accordance with the terms of the license agreement you entered into
* with Sun.
*
* SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
* SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
* IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
* PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
* SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
* THIS SOFTWARE OR ITS DERIVATIVES.
*/

/*
// $Workfile: DSAPrivateKey.java $
// $Revision: 15 $
// $Date: 5/02/00 9:04p $
// $Author: Akuzmin $
// $Archive: /Products/Europa/api21/javacard/security/DSAPrivateKey.java $
// $Modtime: 5/02/00 8:48p $
// Original author:  Andy
// */

package javacard.security;

/**
 * The <code>DSAPrivateKey</code> interface is used to sign data using the DSA algorithm. An
 * implementation of  <code>DSAPrivateKey</code> interface must also implement
 * the <code>DSAKey</code> interface methods.
 * <p>When all four components of the key (X,P,Q,G) are set, the key is
 * initialized and ready for use.
 * <p>
 *
 * @see DSAPublicKey
 * @see KeyBuilder
 * @see Signature
 * @see javacardx.crypto.KeyEncryption
 *
 */

public interface DSAPrivateKey extends PrivateKey, DSAKey {

  /**
   * Sets the value of the key. When the base, prime and subprime parameters are initialized
   * and the key value is set, the key is ready for use.
   * The plaintext data format is big-endian and right-aligned (the least significant bit is the least significant
   * bit of last byte). Input key data is copied into the internal representation.
   * @param buffer the input buffer
   * @param offset the offset into the input buffer at which the modulus value begins
   * @param length the length of the modulus
   * @exception CryptoException with the following reason code:<ul>
   * <li><code>CryptoException.ILLEGAL_VALUE</code> if the input key data length is inconsistent
   * with the implementation or if input data decryption is required and fails.
   * </ul>
   * <p>Note:<ul>
   * <li><em>If the key object implements the </em><code>javacardx.crypto.KeyEncryption</code><em>
   * interface and the </em><code>Cipher</code><em> object specified via </em><code>setKeyCipher()</code><em>
   * is not </em><code>null</code><em>, the key value is decrypted using the </em><code>Cipher</code><em> object.</em>
   * </ul>
   */
    void setX( byte[] buffer, short offset, short length) throws CryptoException;

  /**
   * Returns the value of the key in plain text.
   * The data format is big-endian and right-aligned (the least significant bit is the least significant
   * bit of last byte).
   * @param buffer the output buffer
   * @param offset the offset into the output buffer at which the key value starts
   * @return the byte length of the key value returned
   */
    short getX( byte[] buffer, short offset );

 }
