package org.nem.ncc.controller.requests;

import org.nem.core.model.Address;
import org.nem.core.serialization.Deserializer;
import org.nem.ncc.wallet.*;

public class TransferImportanceRequest extends AccountWalletRequest {
	private final WalletPassword password;
	private final int hoursDue;

	/**
	 * Creates a password / account / wallet request.
	 *
	 * @param address The account address.
	 * @param walletName The wallet name.
	 * @param password The password.
	 */
	public TransferImportanceRequest(final Address address, 
			final WalletName walletName, 
			final WalletPassword password,
			final int hoursDue) {
		super(address, walletName);
		this.password = password;
		this.hoursDue = hoursDue;
	}

	/**
	 * Deserializes a password / account / wallet request.
	 *
	 * @param deserializer The deserializer.
	 */
	public TransferImportanceRequest(final Deserializer deserializer) {
		super(deserializer);
		this.password = WalletPassword.readFrom(deserializer, "password");
		this.hoursDue = deserializer.readInt("hours_due");
	}

	/**
	 * Gets the wallet password.
	 *
	 * @return The wallet password.
	 */
	public WalletPassword getPassword() {
		return this.password;
	}

	/**
	 * Gets the hours due.
	 *
	 * @return The hours due.
	 */
	public int getHoursDue() {
		return this.hoursDue;
	}
}
