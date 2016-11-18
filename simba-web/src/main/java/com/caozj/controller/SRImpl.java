package com.caozj.controller;

import org.springframework.stereotype.Component;

import com.caozj.framework.util.socket.SocketResponseInterface;

@Component
public class SRImpl implements SocketResponseInterface {

	@Override
	public String getReturn(int localPort, String ip, int port, String content) {
		return System.currentTimeMillis() + "," + localPort + ":" + content + "," + ip + "," + port;
	}

}
