package com.bull.ox.sys.security.token;

public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    private Object kaptcha;

    /**
     * 无参构造器
     */
    public UsernamePasswordToken() {

    }

    /**
     * 用户名、密码
     */
    public UsernamePasswordToken(final String username, final char[] password) {
        super(username, password, false, null);
    }

    public UsernamePasswordToken(final String username, final String password) {
        super(username, password != null ? password.toCharArray() : null, false, null);
    }

    /**
     * 用户名、密码、验证码
     */
    public UsernamePasswordToken(final String username, final char[] password, final String kapthca) {
        super(username, password, false, null);
        this.kaptcha = kapthca;
    }

    public UsernamePasswordToken(final String username, final String password, final String kapthca) {
        super(username, password != null ? password.toCharArray() : null, false, null);
        this.kaptcha = kapthca;
    }

    /**
     * 用户名、密码、验证码、主机IP
     */
    public UsernamePasswordToken(final String username, final char[] password, final String kapthca, final String host) {
        super(username, password, false, host);
        this.kaptcha = kapthca;
    }

    public UsernamePasswordToken(final String username, final String password, final String kapthca, final String host) {
        super(username, password != null ? password.toCharArray() : null, false, host);
        this.kaptcha = kapthca;
    }

    /**
     * 用户名、密码、验证码、主机IP、记住我
     */
    public UsernamePasswordToken(final String username, final char[] password, final String kapthca, final String host, final boolean rememberMe) {
        super(username, password, rememberMe, host);
        this.kaptcha = kapthca;
    }

    public UsernamePasswordToken(final String username, final String password, final String kapthca, final String host, final boolean rememberMe) {
        super(username, password != null ? password.toCharArray() : null, rememberMe, host);
        this.kaptcha = kapthca;
    }

    /**
     * 用户名、密码、主机IP、记住我
     */
    public UsernamePasswordToken(final String username, final char[] password, final String host, final boolean rememberMe) {
        super(username, password, rememberMe, host);
    }

    public UsernamePasswordToken(final String username, final String password, final String host, final boolean rememberMe) {
        super(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    public Object getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(Object kaptcha) {
        this.kaptcha = kaptcha;
    }
}
