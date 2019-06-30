package us.codecraft.webmagic.proxy;

/**
 * IP 代理 实体类
 */

public class Proxy {

	private String host; //IP host
	private int port;//IP 端口
	private String username; //用户名
	private String password; //密码

	
	/***
	 * 
	 * Proxy IP代理 构造函数
	 * @param host
	 * @param port
	 */
	public Proxy(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * 
	 * Proxy IP代理构造函数，含用户名，密码
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public Proxy(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Proxy proxy = (Proxy) o;

		if (port != proxy.port) return false;
		if (host != null ? !host.equals(proxy.host) : proxy.host != null) return false;
		if (username != null ? !username.equals(proxy.username) : proxy.username != null) return false;
		return password != null ? password.equals(proxy.password) : proxy.password == null;
	}

	@Override
	public int hashCode() {
		int result = host != null ? host.hashCode() : 0;
		result = 31 * result + port;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Proxy{" +
				"host='" + host + '\'' +
				", port=" + port +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
