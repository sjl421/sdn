package zx.soft.sdn.model;

/**
 * VPN用户地理位置信息模型
 * 
 * @author xuran
 *
 */
public class VPNPostion {

	/**真实号**/
	private String realNumber;
	/**业务IP**/
	private String bizIP;
	/**基站SAC信息或CELLID信息 参考数据34162**/
	private String sac;
	/**基站LAC信息 参考数据25840**/
	private String lac;
	/**时间 yyyy-MM-dd HH:mm:ss**/
	private String time;
	/**基站位置信息**/
	private BaseStation baseStation;

	public VPNPostion() {
		super();
	}

	public String getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(String realNumber) {
		this.realNumber = realNumber;
	}

	public String getBizIP() {
		return bizIP;
	}

	public void setBizIP(String bizIP) {
		this.bizIP = bizIP;
	}

	public String getSac() {
		return sac;
	}

	public void setSac(String sac) {
		this.sac = sac;
	}

	public String getLac() {
		return lac;
	}

	public void setLac(String lac) {
		this.lac = lac;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BaseStation getBaseStation() {
		return baseStation;
	}

	public void setBaseStation(BaseStation baseStation) {
		this.baseStation = baseStation;
	}

}
