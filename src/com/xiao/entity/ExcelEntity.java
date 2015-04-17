package com.xiao.entity;

public class ExcelEntity {
	private String id;
	private String name;
	private String tel;
	private String qq;
	private String weixin;
	private String address;
	public String getString(){
		return id+name+tel+qq+weixin+address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	@Override
	public String toString() {
		return "ExcelEntity [id=" + id + ", name=" + name + ", tel=" + tel
				+ ", qq=" + qq + ", weixin=" + weixin + ", address=" + address
				+ "]";
	}

}
