package org.nem.ncc.controller.requests;

import net.minidev.json.JSONObject;

import org.hamcrest.core.*;
import org.junit.*;

import org.nem.core.crypto.KeyPair;
import org.nem.core.model.Address;
import org.nem.core.model.primitive.Amount;
import org.nem.core.serialization.SerializationException;
import org.nem.ncc.test.*;
import org.nem.ncc.wallet.WalletName;

import java.util.*;
import java.util.function.Consumer;

public class TransferFeeRequestTest {

	@Test
	public void requestCanBeCreated() {
		// Act:
		final Address address = Address.fromPublicKey(new KeyPair().getPublicKey());
		final TransferFeeRequest request = new TransferFeeRequest(new WalletName("w"), address, Address.fromEncoded("r"),
				Amount.fromMicroNem(7), "m", true, 5);

		// Assert:
		Assert.assertThat(request.getWalletName(), IsEqual.equalTo(new WalletName("w")));
		Assert.assertThat(request.getSenderAddress(), IsEqual.equalTo(address));
		Assert.assertThat(request.getRecipientAddress(), IsEqual.equalTo(Address.fromEncoded("r")));
		Assert.assertThat(request.getAmount(), IsEqual.equalTo(Amount.fromMicroNem(7L)));
		Assert.assertThat(request.getMessage(), IsEqual.equalTo("m"));
		Assert.assertThat(request.shouldEncrypt(), IsEqual.equalTo(true));
		Assert.assertThat(request.getHoursDue(), IsEqual.equalTo(5));
	}

	@Test
	public void requestCanBeConverted() {
		// Arrange:
		final Address address = Address.fromPublicKey(new KeyPair().getPublicKey());
		final TransferFeeRequest request = new TransferFeeRequest(new WalletName("w"), address, Address.fromEncoded("r"),
				Amount.fromMicroNem(7), "m", true, 5);

		// Act:
		final AccountWalletRequest convertedRequest = request.toAccountWalletRequest();

		// Assert:
		Assert.assertThat(convertedRequest.getWalletName(), IsEqual.equalTo(new WalletName("w")));
		Assert.assertThat(convertedRequest.getAccountId(), IsEqual.equalTo(address));
	}

	@Test
	public void requestCanBeDeserializedWithAllParameters() {
		// Act:
		final TransferFeeRequest request = this.createRequestFromJson("w", "a", "r", 7L, "m", 8, 5);

		// Assert:
		Assert.assertThat(request.getWalletName(), IsEqual.equalTo(new WalletName("w")));
		Assert.assertThat(request.getSenderAddress(), IsEqual.equalTo(Address.fromEncoded("a")));
		Assert.assertThat(request.getRecipientAddress(), IsEqual.equalTo(Address.fromEncoded("r")));
		Assert.assertThat(request.getAmount(), IsEqual.equalTo(Amount.fromMicroNem(7L)));
		Assert.assertThat(request.getMessage(), IsEqual.equalTo("m"));
		Assert.assertThat(request.shouldEncrypt(), IsEqual.equalTo(true));
		Assert.assertThat(request.getHoursDue(), IsEqual.equalTo(5));
	}

	@Test
	public void requestCanBeDeserializedWithoutMessage() {
		// Act:
		final TransferFeeRequest request = this.createRequestFromJson("w", "a", "r", 7L, null, 0, 5);

		// Assert:
		Assert.assertThat(request.getMessage(), IsNull.nullValue());
		Assert.assertThat(request.shouldEncrypt(), IsEqual.equalTo(false));
	}

	@Test
	public void requestCannotBeDeserializedWithMissingRequiredParameters() {
		// Arrange:
		final List<Consumer<Void>> actions = Arrays.asList(
				v -> this.createRequestFromJson(null, "a", "r", 7L, "m", 8, 5), v -> this.createRequestFromJson("w", null, "r", 7L, "m", 8, 5),
				v -> this.createRequestFromJson("w", "a", null, 7L, "m", 8, 5), v -> this.createRequestFromJson("w", "a", "r", null, "m", 8, 5),
				v -> this.createRequestFromJson("w", "a", "r", 7L, "m", null, 5), v -> this.createRequestFromJson("w", "a", "r", 7L, "m", 8, null));

		// Assert:
		for (final Consumer<Void> action : actions) {
			ExceptionAssert.assertThrows(v -> action.accept(null), SerializationException.class);
		}
	}

	private TransferFeeRequest createRequestFromJson(
			final String walletName,
			final String senderAddress,
			final String recipientAddress,
			final Long amount,
			final String message,
			final Integer shouldEncrypt,
			final Integer hoursDue) {
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("wallet", walletName);
		jsonObject.put("account", senderAddress);
		jsonObject.put("recipient", recipientAddress);
		jsonObject.put("amount", amount);
		jsonObject.put("message", message);
		jsonObject.put("encrypt", shouldEncrypt);
		jsonObject.put("hours_due", hoursDue);
		return new TransferFeeRequest(Utils.createDeserializer(jsonObject));
	}
}