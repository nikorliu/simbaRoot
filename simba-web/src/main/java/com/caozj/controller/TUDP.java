package com.caozj.controller;

import java.io.IOException;
import java.net.DatagramPacket;

import com.caozj.framework.util.udpSocket.UdpSocketUtil;

public class TUDP {

	public static void main(String[] args) throws IOException {
		UdpSocketUtil.start(4564);
		UdpSocketUtil.start(8596);
		new Thread(() -> {
			for (int i = 0; i < 30; i++) {
				try {
					UdpSocketUtil.send(4564, ("4564****" + i).getBytes(), "127.0.0.1", 8596);
					UdpSocketUtil.send(8596, ("8596****" + i).getBytes(), "127.0.0.1", 4564);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			while (true) {
				try {
					DatagramPacket s = UdpSocketUtil.receive(4564, 1024);
					if (s == null) {
						continue;
					}
					System.out.println("4564<-" + s.getAddress().getHostAddress() + ":" + s.getPort() + "@" + new String(s.getData()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(() -> {
			while (true) {
				try {
					DatagramPacket s = UdpSocketUtil.receive(8596, 1024);
					if (s == null) {
						continue;
					}
					System.out.println("8596<-" + s.getAddress().getHostAddress() + ":" + s.getPort() + "@" + new String(s.getData()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
