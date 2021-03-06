package org.nem.ncc.controller;

import net.minidev.json.JSONObject;
import org.hamcrest.core.IsEqual;
import org.junit.*;
import org.mockito.Mockito;
import org.nem.ncc.controller.requests.WalletNamePasswordBag;
import org.nem.ncc.controller.viewmodels.WalletViewModel;
import org.nem.ncc.services.*;
import org.nem.ncc.test.Utils;
import org.nem.ncc.wallet.*;

public class WalletControllerTest {

	//region create / open / info / close

	@Test
	public void createDelegatesToWalletServicesAndMapper() {
		// Arrange:
		final WalletNamePasswordPair request = new WalletNamePasswordPair(new WalletName("wal"), new WalletPassword("pwd"));
		final Wallet wallet = Mockito.mock(Wallet.class);
		final WalletViewModel walletViewModel = Mockito.mock(WalletViewModel.class);
		final TestContext context = new TestContext();
		Mockito.when(context.walletServices.create(request)).thenReturn(wallet);
		Mockito.when(context.walletMapper.toViewModel(wallet)).thenReturn(walletViewModel);

		// Act:
		final WalletViewModel result = context.controller.create(request);

		// Assert:
		Assert.assertThat(result, IsEqual.equalTo(walletViewModel));
		Mockito.verify(context.walletServices, Mockito.times(1)).create(request);
		Mockito.verify(context.walletMapper, Mockito.times(1)).toViewModel(wallet);
	}

	@Test
	public void openDelegatesToWalletServicesAndMapper() {
		// Arrange:
		final WalletNamePasswordPair request = new WalletNamePasswordPair(new WalletName("wal"), new WalletPassword("pwd"));
		final Wallet wallet = Mockito.mock(Wallet.class);
		final WalletViewModel walletViewModel = Mockito.mock(WalletViewModel.class);
		final TestContext context = new TestContext();
		Mockito.when(context.walletServices.open(request)).thenReturn(wallet);
		Mockito.when(context.walletMapper.toViewModel(wallet)).thenReturn(walletViewModel);

		// Act:
		final WalletViewModel result = context.controller.open(request);

		// Assert:
		Assert.assertThat(result, IsEqual.equalTo(walletViewModel));
		Mockito.verify(context.walletServices, Mockito.times(1)).open(request);
		Mockito.verify(context.walletMapper, Mockito.times(1)).toViewModel(wallet);
	}

	@Test
	public void infoDelegatesToWalletServicesAndMapper() {
		// Arrange:
		final WalletName request = new WalletName("wal");
		final Wallet wallet = Mockito.mock(Wallet.class);
		final WalletViewModel walletViewModel = Mockito.mock(WalletViewModel.class);
		final TestContext context = new TestContext();
		Mockito.when(context.walletServices.get(request)).thenReturn(wallet);
		Mockito.when(context.walletMapper.toViewModel(wallet)).thenReturn(walletViewModel);

		// Act:
		final WalletViewModel result = context.controller.info(request);

		// Assert:
		Assert.assertThat(result, IsEqual.equalTo(walletViewModel));
		Mockito.verify(context.walletServices, Mockito.times(1)).get(request);
		Mockito.verify(context.walletMapper, Mockito.times(1)).toViewModel(wallet);
	}

	@Test
	public void closeDelegatesToWalletServices() {
		// Arrange:
		final WalletName request = new WalletName("wal");
		final TestContext context = new TestContext();

		// Act:
		context.controller.close(request);

		// Assert:
		Mockito.verify(context.walletServices, Mockito.times(1)).close(request);
	}

	//endregion

	//region changePassword / changeWalletName

	@Test
	public void changePasswordDelegatesToWalletServices() {
		// Arrange:
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("wallet", "w1");
		jsonObject.put("password", "p1");
		jsonObject.put("new_password", "p2");
		final WalletNamePasswordBag bag = new WalletNamePasswordBag(Utils.createDeserializer(jsonObject));
		final TestContext context = new TestContext();

		// Act:
		context.controller.changePassword(bag);

		// Assert:
		Mockito.verify(context.walletServices, Mockito.times(1))
				.move(new WalletNamePasswordPair("w1", "p1"), new WalletNamePasswordPair("w1", "p2"));
	}

	@Test
	public void changeNameDelegatesToWalletServices() {
		// Arrange:
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("wallet", "w1");
		jsonObject.put("password", "p1");
		jsonObject.put("new_name", "w2");
		final WalletNamePasswordBag bag = new WalletNamePasswordBag(Utils.createDeserializer(jsonObject));
		final TestContext context = new TestContext();

		// Act:
		context.controller.changeName(bag);

		// Assert:
		Mockito.verify(context.walletServices, Mockito.times(1))
				.move(new WalletNamePasswordPair("w1", "p1"), new WalletNamePasswordPair("w2", "p1"));
	}

	//endregion

	private static class TestContext {
		private final WalletServices walletServices = Mockito.mock(WalletServices.class);
		private final WalletMapper walletMapper = Mockito.mock(WalletMapper.class);
		private final WalletController controller = new WalletController(this.walletServices, this.walletMapper);
	}
}