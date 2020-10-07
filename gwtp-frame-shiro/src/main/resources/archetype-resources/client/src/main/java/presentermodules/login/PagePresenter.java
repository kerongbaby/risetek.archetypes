package ${package}.presentermodules.login;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.Title;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ${package}.NameTokens;
import ${package}.entry.Subject;
import ${package}.entry.UserRolesChangeEvent;
import ${package}.entry.UserRolesChangeEvent.UserRolesChangeHandler;

public class PagePresenter extends
		Presenter<PagePresenter.MyView, PagePresenter.MyProxy>
		implements MyUiHandlers, UserRolesChangeHandler {

	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
		public void setStatus(String status);
		public void setAccountOperatorActive(boolean enable);
	}

	private final String status_loginfailed = "\u767b\u5f55\u5931\u8d25";
	
	private final Subject subject;
    private final PlaceManager placeManager;
	
    @Title("Login")
	@ProxyCodeSplit
	@NameToken(NameTokens.login)
    @NoGatekeeper
	public interface MyProxy extends ProxyPlace<PagePresenter> {}

	@Inject
	public PagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, PlaceManager placeManager, Subject subject) {
		super(eventBus, view, proxy, RevealType.Root);
		getView().setUiHandlers(this);
		eventBus.addHandler(UserRolesChangeEvent.getType(), this);
		this.placeManager = placeManager;
		this.subject = subject;
		
		// TODO: get the project for OAuth service.
	}

	@Override
	public void Login(String username, String password, boolean rememberme, String project) {
		if(null == username || username.isEmpty() || null == password || password.isEmpty()) {
			getView().setStatus(status_loginfailed);
			return;
		}

		subject.Login(username, password, rememberme, project, c->getView().setStatus(status_loginfailed));
	}
	
	
	@Override
	public void onUserStatusChange() {
		GWT.log("user status changed by login presenter");
		if(subject.isLogin()) {
			GWT.log("user had login");
			placeManager.revealDefaultPlace();
		}
		else
			GWT.log("user is logout");
	}

	@Override
	public void newAccount() {
		PlaceRequest placeRequest = new PlaceRequest.Builder()
				                        .with("continue", NameTokens.login)
				                        .nameToken(NameTokens.newAcct).build();
		placeManager.revealPlace(placeRequest);
	}

	@Override
	public void resetPassword() {
		PlaceRequest placeRequest = new PlaceRequest.Builder()
                .with("continue", NameTokens.login)
                .nameToken(NameTokens.resetPassword).build();
		placeManager.revealPlace(placeRequest);
	}
}
