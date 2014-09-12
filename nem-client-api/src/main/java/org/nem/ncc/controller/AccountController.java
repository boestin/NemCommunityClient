package org.nem.ncc.controller;

import org.nem.core.connect.HttpJsonPostRequest;
import org.nem.core.connect.client.*;
import org.nem.core.crypto.PrivateKey;
import org.nem.core.model.Address;
import org.nem.core.model.ncc.HarvestInfo;
import org.nem.core.model.primitive.BlockHeight;
import org.nem.core.node.NodeEndpoint;
import org.nem.core.serialization.*;
import org.nem.ncc.connector.*;
import org.nem.ncc.controller.annotations.RequiresTrustedNis;
import org.nem.ncc.controller.requests.*;
import org.nem.ncc.controller.viewmodels.*;
import org.nem.ncc.services.*;
import org.nem.ncc.wallet.WalletAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles requests related to the REST resource "account".
 */
@RestController
public class AccountController {
	private final AccountServices accountServices;
	private final AccountMapper accountMapper;
	private final WalletServices walletServices;
	private final ChainServices chainServices;
	private final PrimaryNisConnector nisConnector;
	private final AsyncNisConnector cloudConnector;

	/**
	 * Creates a new account controller.
	 *
	 * @param accountServices The account services.
	 * @param accountMapper The account mapper.
	 * @param walletServices The wallet services.
	 * @param chainServices The chain services.
	 * @param nisConnector The NIS connector.
	 * @param cloudConnector The cloud connector.
	 */
	@Autowired(required = true)
	public AccountController(
			final AccountServices accountServices,
			final AccountMapper accountMapper,
			final WalletServices walletServices,
			final ChainServices chainServices,
			final PrimaryNisConnector nisConnector,
			final AsyncNisConnector cloudConnector) {
		this.accountServices = accountServices;
		this.accountMapper = accountMapper;
		this.walletServices = walletServices;
		this.chainServices = chainServices;
		this.nisConnector = nisConnector;
		this.cloudConnector = cloudConnector;
	}

	//region find

	/**
	 * Gets information about the specified account.
	 * Looks-up an account which is known by its address id
	 *
	 * @param aidRequest The account identifier.
	 * @return The account information.
	 */
	@RequestMapping(value = "/account/find", method = RequestMethod.POST)
	public AccountViewModel getAccountInfo(@RequestBody final AccountIdRequest aidRequest) {
		final Address address = aidRequest.getAccountId();
		return this.accountMapper.toViewModel(address);
	}

	//endregion

	//region transactions/unconfirmed

	/**
	 * Gets information about the specified account and unconfirmed transactions.
	 *
	 * @param ahRequest The account identifier.
	 * @return The account and transactions information.
	 */
	@RequestMapping(value = "/account/transactions/unconfirmed", method = RequestMethod.POST)
	public AccountTransactionsPair getAccountTransactionsUnconfirmed(@RequestBody final AccountHashRequest ahRequest) {
		final AccountViewModel account = this.getAccountInfo(ahRequest);
		return new AccountTransactionsPair(account, this.getUnconfirmedTransactions(ahRequest));
	}

	private Collection<TransferViewModel> getUnconfirmedTransactions(final AccountHashRequest ahRequest) {
		final Address address = ahRequest.getAccountId();
		return this.accountServices.getUnconfirmedTransactions(address).stream()
				.map(t -> new TransferViewModel(t, address))
				.collect(Collectors.toList());
	}

	//endregion

	//region transactions/(all|confirmed|incoming|outgoing)

	/**
	 * Gets information about the specified account and incoming and outgoing confirmed and unconfirmed transactions.
	 *
	 * @param ahRequest The account identifier.
	 * @return The account and transactions information.
	 */
	@RequestMapping(value = "/account/transactions/all", method = RequestMethod.POST)
	public AccountTransactionsPair getAccountTransactionsAll(@RequestBody final AccountHashRequest ahRequest) {
		final AccountViewModel account = this.getAccountInfo(ahRequest);
		final List<TransferViewModel> allTransfers = new ArrayList<>();
		allTransfers.addAll(this.getUnconfirmedTransactions(ahRequest));
		allTransfers.addAll(this.getConfirmedTransactions(TransactionDirection.ALL, ahRequest));
		return new AccountTransactionsPair(account, allTransfers);
	}

	/**
	 * Gets information about the specified account and incoming and outgoing confirmed transactions.
	 *
	 * @param ahRequest The account identifier.
	 * @return The account and transactions information.
	 */
	@RequestMapping(value = "/account/transactions/confirmed", method = RequestMethod.POST)
	public AccountTransactionsPair getAccountTransactionsConfirmed(@RequestBody final AccountHashRequest ahRequest) {
		return this.getAccountTransactions(TransactionDirection.ALL, ahRequest);
	}

	/**
	 * Gets information about the specified account and incoming confirmed transactions.
	 *
	 * @param ahRequest The account identifier.
	 * @return The account and transactions information.
	 */
	@RequestMapping(value = "/account/transactions/incoming", method = RequestMethod.POST)
	public AccountTransactionsPair getAccountTransactionsIncoming(@RequestBody final AccountHashRequest ahRequest) {
		return this.getAccountTransactions(TransactionDirection.INCOMING, ahRequest);
	}

	/**
	 * Gets information about the specified account and outgoing confirmed transactions.
	 *
	 * @param ahRequest The account identifier.
	 * @return The account and transactions information.
	 */
	@RequestMapping(value = "/account/transactions/outgoing", method = RequestMethod.POST)
	public AccountTransactionsPair getAccountTransactionsOutgoing(@RequestBody final AccountHashRequest ahRequest) {
		return this.getAccountTransactions(TransactionDirection.OUTGOING, ahRequest);
	}

	private AccountTransactionsPair getAccountTransactions(final TransactionDirection direction, final AccountHashRequest ahRequest) {
		final AccountViewModel account = this.getAccountInfo(ahRequest);
		return new AccountTransactionsPair(account, this.getConfirmedTransactions(direction, ahRequest));
	}

	private Collection<TransferViewModel> getConfirmedTransactions(final TransactionDirection direction, final AccountHashRequest ahRequest) {
		final Address address = ahRequest.getAccountId();
		final BlockHeight lastBlockHeight = this.nisConnector.forward(this.chainServices::getLastBlockHeightAsync);
		return this.accountServices.getTransactions(direction, address, ahRequest.getHash()).stream()
				.map(p -> new TransferViewModel(p, address, lastBlockHeight))
				.collect(Collectors.toList());
	}

	//endregion

	//region harvests

	/**
	 * Retrieves a list of infos on harvested blocks for an account.
	 *
	 * @param atsRequest The account / timestamp view model.
	 * @return The list of harvest infos.
	 */
	@RequestMapping(value = "/account/harvests", method = RequestMethod.POST)
	public SerializableList<HarvestInfoViewModel> getAccountHarvests(@RequestBody final AccountTimeStampRequest atsRequest) {
		final Deserializer deserializer = this.nisConnector.get(NisApiId.NIS_REST_ACCOUNT_HARVESTS, formatHarvestQuery(atsRequest));
		final List<HarvestInfo> harvestInfos = deserializer.readObjectArray("data", HarvestInfo::new);
		return new SerializableList<>(harvestInfos.stream().map(HarvestInfoViewModel::new).collect(Collectors.toList()));
	}

	private static String formatHarvestQuery(final AccountTimeStampRequest atsRequest) {
		final StringBuilder builder = new StringBuilder();
		builder.append("address=");
		builder.append(atsRequest.getAccountId());

		if (null != atsRequest.getTimeStamp()) {
			builder.append("&timeStamp=");
			builder.append(atsRequest.getTimeStamp());
		}

		return builder.toString();
	}

	//endregion

	//region unlock|lock

	/**
	 * Unlock the account on the connected NIS server (start foraging).
	 *
	 * @param awRequest The account / wallet view model.
	 */
	@RequestMapping(value = "/wallet/account/unlock", method = RequestMethod.POST)
	@RequiresTrustedNis
	public void unlock(@RequestBody final AccountWalletRequest awRequest) {
		this.nisConnector.voidPost(NisApiId.NIS_REST_ACCOUNT_UNLOCK, new HttpJsonPostRequest(this.getPrivateKey(awRequest)));
	}

	/**
	 * Lock the account on the connected NIS server (stop foraging).
	 *
	 * @param awRequest The account / wallet view model.
	 */
	@RequestMapping(value = "/wallet/account/lock", method = RequestMethod.POST)
	@RequiresTrustedNis
	public void lock(@RequestBody final AccountWalletRequest awRequest) {
		this.nisConnector.voidPost(NisApiId.NIS_REST_ACCOUNT_LOCK, new HttpJsonPostRequest(this.getPrivateKey(awRequest)));
	}

	private PrivateKey getPrivateKey(final AccountWalletRequest awRequest) {
		return this.walletServices.get(awRequest.getWalletName())
				.getAccountPrivateKey(awRequest.getAccountId());
	}

	/**
	 * Unlock the account on a remote NIS server (start foraging).
	 * Remote address being used has to be announced previously.
	 *
	 * @param awRequest The account / wallet view model.
	 */
	@RequestMapping(value = "/wallet/account/remote/unlock", method = RequestMethod.POST)
	@RequiresTrustedNis
	public void remoteUnlock(@RequestBody final RemoteHarvestRequest awRequest) {
		NodeEndpoint endpoint = awRequest.getEndpoint();
		PrimaryNisConnector remoteConnector = new DefaultNisConnector(
				() -> endpoint,
				this.cloudConnector);
		WalletAccount account = this.walletServices.tryFindOpenAccount(awRequest.getAccountId());
		account.setRemoteHarvestingEndpoint(endpoint);
		remoteConnector.voidPost(NisApiId.NIS_REST_ACCOUNT_UNLOCK, new HttpJsonPostRequest(this.getPrivateKey(awRequest)));
	}

	/**
	 * Lock the account on a remote NIS server (stop foraging).
	 *
	 * @param awRequest The account / wallet view model.
	 */
	@RequestMapping(value = "/wallet/account/remote/lock", method = RequestMethod.POST)
	@RequiresTrustedNis
	public void remoteLock(@RequestBody final RemoteHarvestRequest awRequest) {
		NodeEndpoint endpoint = awRequest.getEndpoint();
		PrimaryNisConnector remoteConnector = new DefaultNisConnector(
				() -> endpoint,
				this.cloudConnector);
		remoteConnector.voidPost(NisApiId.NIS_REST_ACCOUNT_LOCK, new HttpJsonPostRequest(this.getPrivateKey(awRequest)));
		WalletAccount account = this.walletServices.tryFindOpenAccount(awRequest.getAccountId());
		account.setRemoteHarvestingEndpoint(null);
	}

	//endregion

}
