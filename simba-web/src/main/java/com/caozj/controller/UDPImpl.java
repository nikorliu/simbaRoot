package com.caozj.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.udpSocket.UdpDealInterface;

@Component
public class UDPImpl implements UdpDealInterface {
	
	private static final Log logger = LogFactory.getLog(UDPImpl.class);

	@Override
	public void deal(int localPort, String remoteIp, int remotePort, byte[] content) {
		logger.info(localPort + "," + remoteIp + "," + remotePort + "," + new String(content));
	}

}
