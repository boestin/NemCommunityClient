package org.nem.ncc.wallet;

import org.hamcrest.core.*;
import org.junit.*;
import org.nem.core.crypto.*;
import org.nem.core.model.Address;
import org.nem.ncc.test.*;

public class WalletAccountTest {

	//region construction

	@Test
	public void accountCanBeCreatedAroundGeneratedPrivateKey() {
		// Act:
		final WalletAccount account = new WalletAccount();

		// Assert:
		final Address expectedAddress = Address.fromPublicKey(new KeyPair(account.getPrivateKey()).getPublicKey());
		Assert.assertThat(account.getPrivateKey(), IsNull.notNullValue());
		Assert.assertThat(account.getAddress(), IsEqual.equalTo(expectedAddress));
	}

	@Test
	public void accountGeneratesDifferentPrivateKeys() {
		// Act:
		final WalletAccount account1 = new WalletAccount();
		final WalletAccount account2 = new WalletAccount();

		// Assert:
		Assert.assertThat(account2.getAddress(), IsNot.not(IsEqual.equalTo(account1.getAddress())));
	}

	@Test
	public void accountCanBeCreatedAroundPrivateKey() {
		// Arrange:
		final PrivateKey privateKey = new KeyPair().getPrivateKey();

		// Act:
		final WalletAccount account = new WalletAccount(privateKey);

		// Assert:
		Assert.assertThat(account.getAddress(), IsEqual.equalTo(getAddressFromPrivateKey(privateKey)));
		Assert.assertThat(account.getPrivateKey(), IsEqual.equalTo(privateKey));
	}

	@Test
	public void accountCannotBeCreatedWithMissingParameters() {
		// Assert:
		ExceptionAssert.assertThrows(v -> new WalletAccount((PrivateKey)null), IllegalArgumentException.class);
	}

	//endregion

	//region serialization

	@Test
	public void accountCanBeRoundTripped() {
		// Arrange:
		final PrivateKey privateKey = new KeyPair().getPrivateKey();

		// Act:
		final WalletAccount account = new WalletAccount(
				Utils.roundtripSerializableEntity(new WalletAccount(privateKey), null));

		// Assert:
		Assert.assertThat(account.getAddress(), IsEqual.equalTo(getAddressFromPrivateKey(privateKey)));
		Assert.assertThat(account.getPrivateKey(), IsEqual.equalTo(privateKey));
	}

	//endregion

	//region equals / hashCode / toString

	@Test
	public void equalsOnlyReturnsTrueForEquivalentObjects() {
		// Arrange:
		final PrivateKey privateKey = new KeyPair().getPrivateKey();
		final WalletAccount account = new WalletAccount(privateKey);

		// Assert:
		Assert.assertThat(new WalletAccount(privateKey), IsEqual.equalTo(account));
		Assert.assertThat(new WalletAccount(new KeyPair().getPrivateKey()), IsNot.not(IsEqual.equalTo(account)));
		Assert.assertThat(null, IsNot.not(IsEqual.equalTo(account)));
		Assert.assertThat(privateKey, IsNot.not(IsEqual.equalTo((Object)account)));
	}

	@Test
	public void hashCodesAreEqualForEquivalentObjects() {
		// Arrange:
		final PrivateKey privateKey = new KeyPair().getPrivateKey();
		final WalletAccount account = new WalletAccount(privateKey);
		final int hashCode = account.hashCode();

		// Assert:
		Assert.assertThat(new WalletAccount(privateKey).hashCode(), IsEqual.equalTo(hashCode));
		Assert.assertThat(new WalletAccount(new KeyPair().getPrivateKey()).hashCode(), IsNot.not(IsEqual.equalTo(hashCode)));
	}

	@Test
	public void toStringReturnsAddressString() {
		// Arrange:
		final PrivateKey privateKey = new KeyPair().getPrivateKey();
		final WalletAccount account = new WalletAccount(privateKey);

		// Assert:
		Assert.assertThat(account.toString(), IsEqual.equalTo(getAddressFromPrivateKey(privateKey).toString()));
	}

	//endregion

	private static Address getAddressFromPrivateKey(final PrivateKey key) {
		return Address.fromPublicKey(new KeyPair(key).getPublicKey());
	}
}