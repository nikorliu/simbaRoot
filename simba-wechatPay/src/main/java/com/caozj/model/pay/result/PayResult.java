package com.caozj.model.pay.result;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.caozj.framework.util.common.XmlUtil;

/**
 * 支付结果通知对象
 * 
 * @author caozhejun
 *
 */
@XStreamAlias("xml")
public class PayResult {

	/**
	 * SUCCESS/FAIL
	 * 
	 * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 * 
	 */
	private String return_code;

	/**
	 * 返回信息，如非空，为错误原因 签名失败 参数格式校验错误
	 */
	private String return_msg;

	/**
	 * 微信支付分配的公众账号ID（企业号corpid即为此appId）
	 */
	private String appid;

	/**
	 * 微信支付分配的商户号
	 */
	private String mch_id;

	/**
	 * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	 */
	private String device_info;

	/**
	 * 随机字符串，长度要求在32位以内。
	 */
	private String nonce_str;

	/**
	 * 通过签名算法计算得出的签名值
	 */
	private String sign;

	/**
	 * 签名类型，默认为MD5，支持HMAC-SHA256和MD5
	 */
	private String sign_type = "MD5";

	/**
	 * 业务结果 SUCCESS/FAIL
	 */
	private String result_code;

	/**
	 * 错误代码
	 */
	private String err_code;

	/**
	 * 错误代码描述
	 */
	private String err_code_des;

	/**
	 * 用户在商户appid下的唯一标识
	 */
	private String openid;

	/**
	 * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	 */
	private String is_subscribe;

	/**
	 * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，
	 */
	private String trade_type;

	/**
	 * 银行类型，采用字符串类型的银行标识
	 */
	private String bank_type;

	/**
	 * 订单总金额，单位为分
	 */
	private Integer total_fee;

	/**
	 * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
	 */
	private int settlement_total_fee;

	/**
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 */
	private String fee_type = "CNY";

	/**
	 * 现金支付金额订单现金支付金额
	 */
	private Integer cash_fee;

	/**
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 */
	private String cash_fee_type = "CNY";

	/**
	 * “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额
	 */
	private int coupon_fee;

	/**
	 * 代金券使用数量
	 */
	private Integer coupon_count;

	/**
	 * 微信支付订单号
	 */
	private String transaction_id;

	/**
	 * 商户系统的订单号，与请求一致。
	 */
	private String out_trade_no;

	/**
	 * 附加数据，原样返回
	 */
	private String attach;

	/**
	 * 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	 */
	private String time_end;

	private List<Coupon> coupons;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public int getSettlement_total_fee() {
		return settlement_total_fee;
	}

	public void setSettlement_total_fee(int settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public Integer getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public int getCoupon_fee() {
		return coupon_fee;
	}

	public void setCoupon_fee(int coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public Integer getCoupon_count() {
		return coupon_count;
	}

	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String toXML() {
		return XmlUtil.fromObject(this);
	}

	@Override
	public String toString() {
		return toXML();
	}

	public static class Coupon {
		/**
		 * <pre>
		 * 代金券类型
		 * coupon_type_$n
		 * 否
		 * String
		 * CASH
		 * <li>CASH--充值代金券
		 * <li>NO_CASH---非充值代金券
		 * 	订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
		 * </pre>
		 */
		private String couponType;

		/**
		 * <pre>
		 * 代金券ID
		 * coupon_id_$n
		 * 否
		 * String(20)
		 * 10000
		 * 代金券ID, $n为下标，从0开始编号
		 * </pre>
		 */
		private String couponId;

		/**
		 * <pre>
		 * 单个代金券支付金额
		 * coupon_fee_$n
		 * 否
		 * Int
		 * 100
		 * 单个代金券支付金额, $n为下标，从0开始编号
		 * </pre>
		 */
		private Integer couponFee;

		public Coupon(String couponType, String couponId, Integer couponFee) {
			this.couponType = couponType;
			this.couponId = couponId;
			this.couponFee = couponFee;
		}

		public String getCouponType() {
			return this.couponType;
		}

		public void setCouponType(String couponType) {
			this.couponType = couponType;
		}

		public String getCouponId() {
			return this.couponId;
		}

		public void setCouponId(String couponId) {
			this.couponId = couponId;
		}

		public Integer getCouponFee() {
			return this.couponFee;
		}

		public void setCouponFee(Integer couponFee) {
			this.couponFee = couponFee;
		}

	}

	/**
	 * 通过xml组装coupons属性内容
	 * 
	 * @throws XPathExpressionException
	 * @throws DOMException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void composeCoupons(String xml) throws DOMException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		if (this.coupon_count != null && this.coupon_count > 0) {
			this.coupons = Lists.newArrayList();
			Document doc = XmlUtil.parseXMLContent(xml);
			Element root = doc.getDocumentElement();
			for (int i = 0; i < this.coupon_count; i++) {
				String type = ((Element) XmlUtil.selectSingleNode("/xml/coupon_type_" + i, root)).getTextContent();
				String id = ((Element) XmlUtil.selectSingleNode("/xml/coupon_id_" + i, root)).getTextContent();
				String fee = ((Element) XmlUtil.selectSingleNode("/xml/coupon_fee_" + i, root)).getTextContent();
				this.coupons.add(new Coupon(type, id, NumberUtils.toInt(fee)));
			}
		}
	}
}
