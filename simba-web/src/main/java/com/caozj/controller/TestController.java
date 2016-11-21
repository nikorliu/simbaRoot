package com.caozj.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.util.socket.SocketChannelClientUtil;
import com.caozj.framework.util.socket.SocketChannelServerUtil;
import com.caozj.framework.util.udpSocket.NIOUdpServerUtil;
import com.caozj.framework.util.udpSocket.UdpSocketUtil;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping
	public String test(ModelMap model, String info) {
		model.put("message", info);
		return "message";
	}

	@PostConstruct
	private void init() throws IOException {
		// SocketServerUtil.start(8989);
		SocketChannelServerUtil.start(8989);
		NIOUdpServerUtil.start(6969);
		UdpSocketUtil.start(8520);
	}

	@RequestMapping
	public String sendUdp(ModelMap model) throws IOException {
		for (int i = 0; i < 15; i++) {
			UdpSocketUtil.send(8520, (8520 + "," + i).getBytes(), "127.0.0.1", 6969);
		}

		String r = "";
		for (byte[] b : NIOUdpServerUtil.getResp(6969)) {
			r += new String(b) + ",";
		}
		model.put("message", "*********" + r);
		return "message";
	}

	@RequestMapping
	public String sendSocket(ModelMap model, String info) throws IOException {
		// SocketClientUtil.sendContent("127.0.0.1", 8989, info);
		// String r = SocketClientUtil.sendAndGetReply("127.0.0.1", 8989,
		// info,9000000);
		// List<String> rl = SocketServerUtil.getResp(8989);
		// model.put("message", r + "*********" + rl.toString());
		// model.put("message", "*********" + rl.toString());
		byte[] r = SocketChannelClientUtil.send("127.0.0.1", 8989, info.getBytes());
		List<byte[]> rl = SocketChannelServerUtil.getResp(8989);
		String s = "";
		s += new String(r) + "@";
		for (byte[] b : rl) {
			s += new String(b) + ",";
		}
		model.put("message", "*********" + s);
		return "message";
	}
}
