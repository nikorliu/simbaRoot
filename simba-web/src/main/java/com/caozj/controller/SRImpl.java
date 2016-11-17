package com.caozj.controller;

import org.springframework.stereotype.Component;

import com.caozj.framework.util.socket.SocketResponseInterface;

@Component
public class SRImpl implements SocketResponseInterface {

	@Override
	public String getReturn(String ip, int port, String content) {
		return System.currentTimeMillis() + ":" + content + "," + ip + "," + port;
	}

}
