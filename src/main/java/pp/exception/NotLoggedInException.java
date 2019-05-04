package pp.exception;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class NotLoggedInException extends RuntimeException {
    public boolean wrongCaptcha;
    public boolean wrongLogin;
    public boolean wrongPassword;

    public NotLoggedInException(boolean wrongCaptcha, boolean wrongLogin, boolean wrongPassword) {
        this.wrongCaptcha = wrongCaptcha;
        this.wrongLogin = wrongLogin;
        this.wrongPassword = wrongPassword;
    }

    public NotLoggedInException() {
	}

	public NotLoggedInException(final String message) {
		super(message);
	}

	public NotLoggedInException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NotLoggedInException(final Throwable cause) {
		super(cause);
	}

    @Override
    public String getMessage() {
        return "" + (wrongCaptcha ? " wrongCaptcha " : "") + (wrongLogin ? " wrong Login " : "") + (wrongPassword ? " wrong Password " : "");
    }
}
