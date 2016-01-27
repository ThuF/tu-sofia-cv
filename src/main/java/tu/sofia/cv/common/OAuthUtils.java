package tu.sofia.cv.common;

import java.util.Properties;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OAuthUtils {

	private DestinationUtils destinationUtils;

	@Inject
	public OAuthUtils(DestinationUtils destinationUtils) {
		this.destinationUtils = destinationUtils;
	}

	public String authenticate(HttpServletRequest request, String authProvider, String destinationName) throws Exception {
		Properties oAuthProperties = new Properties();
		oAuthProperties.putAll(destinationUtils.getDestnation(ICommonConstants.Destinations.Names.OAUTH).getAllProperties());
		SocialAuthConfig config = SocialAuthConfig.getDefault();
		config.load(oAuthProperties);

		SocialAuthManager manager = new SocialAuthManager();
		manager.setSocialAuthConfig(config);
		String url = manager.getAuthenticationUrl(authProvider, getSuccessCallback(destinationName));
		setSessionProperties(request, manager);
		return url;
	}

	public AuthProvider getAuthProvider(HttpServletRequest request) throws Exception {
		return getAuthManager(request).connect(SocialAuthUtil.getRequestParametersMap(request));
	}

	public String getSuccessAuthRedirectUrl(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(ICommonConstants.Session.Properties.AUTH_REDIRECT_LOCATION);
	}

	private String getSuccessCallback(String destinationName) throws NamingException {
		return destinationUtils.getDestnation(destinationName).getProperty(ICommonConstants.Destinations.Properties.CALLBACK);
	}

	private static void setSessionProperties(HttpServletRequest request, SocialAuthManager manager) {
		HttpSession session = request.getSession();
		session.setAttribute(ICommonConstants.Session.Properties.AUTH_MANAGER, manager);
		session.setAttribute(ICommonConstants.Session.Properties.AUTH_REDIRECT_LOCATION, request.getHeader(ICommonConstants.Headers.Names.REFERER));
	}

	private static SocialAuthManager getAuthManager(HttpServletRequest request) {
		return (SocialAuthManager) request.getSession().getAttribute(ICommonConstants.Session.Properties.AUTH_MANAGER);
	}
}
