package com.caozj.controller;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class NioUdpServer {

	public static void main(String[] args) {
		new Thread(() -> {
			try {
				Selector selector = Selector.open();
				DatagramChannel channel = DatagramChannel.open();
				channel.configureBlocking(false);
				DatagramSocket socket = channel.socket();
				socket.bind(new InetSocketAddress(1000));
				channel.register(selector, SelectionKey.OP_READ);

				ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
				while (true) {
					int n = selector.select();
					if (n == 0) {
						continue;
					}
					Set<SelectionKey> readyKeys = selector.selectedKeys();
					for (SelectionKey key : readyKeys) {
						readyKeys.remove(key);

						if (key.isReadable()) {
							DatagramChannel dc = (DatagramChannel) key.channel();
							InetSocketAddress client = (InetSocketAddress) dc.receive(receiveBuffer); // 接收来自任意一个Client的数据报
							key.interestOps(SelectionKey.OP_READ);
							System.out.println("client ----> IP: " + client.getAddress().getHostAddress() + ", port: " + client.getPort());
							System.out.println(new String(receiveBuffer.array()));
							receiveBuffer.clear();
						} // if
					}
				} // while
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

}