package com.caozj.interfaces;

import com.caozj.model.wxHardware.receive.BindResponseEvent;
import com.caozj.model.wxHardware.receive.DeviceRequestMsg;
import com.caozj.model.wxHardware.receive.DeviceResponseMsg;

public interface HardwareDealInterface {

	DeviceResponseMsg text(DeviceRequestMsg request);

	BindResponseEvent bind(DeviceRequestMsg request);

	BindResponseEvent unbind(DeviceRequestMsg request);
}
