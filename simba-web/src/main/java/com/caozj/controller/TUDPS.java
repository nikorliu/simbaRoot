package com.caozj.controller;

import java.io.IOException;

import com.caozj.framework.util.udpSocket.UdpSocketUtil;

public class TUDPS {

	public static void main(String[] args) throws IOException {
		UdpSocketUtil.start(8596);
		new Thread(() -> {
			for (int i = 1; i < 300; i++) {
				try {
					UdpSocketUtil.send(8596, ("8596****" + i).getBytes(), "127.0.0.1", 6969);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
