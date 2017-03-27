package com.simba.sigar.service.impl;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simba.framework.util.common.SystemUtil;
import com.simba.framework.util.data.MathUtil;
import com.simba.framework.util.date.DateUtil;
import com.simba.framework.util.jdbc.Pager;
import com.simba.framework.util.sigar.SigarUtil;
import com.simba.sigar.dao.CapabilityDao;
import com.simba.sigar.model.Capability;
import com.simba.sigar.model.enums.CapabilityType;
import com.simba.sigar.service.CapabilityService;

/**
 * 性能Service实现类
 * 
 * @author caozj
 * 
 */
@Service
@Transactional
public class CapabilityServiceImpl implements CapabilityService {

  @Autowired
  private CapabilityDao capabilityDao;

  @Override
  public void add(Capability capability) {
    capabilityDao.add(capability);
  }

  @Override
  public void delete(long id) {
    capabilityDao.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Capability get(long id) {
    return capabilityDao.get(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> page(Pager page) {
    return capabilityDao.page(page);
  }

  @Override
  @Transactional(readOnly = true)
  public int count() {
    return capabilityDao.count();
  }

  @Override
  @Transactional(readOnly = true)
  public int countBy(String field, Object value) {
    return capabilityDao.countBy(field, value);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> listAll() {
    return capabilityDao.listAll();
  }

  @Override
  public void update(Capability capability) {
    capabilityDao.update(capability);
  }

  @Override
  public void batchDelete(List<Integer> idList) {
    for (Integer id : idList) {
      this.delete(id);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Capability getBy(String field, Object value) {
    return capabilityDao.getBy(field, value);
  }

  @Override
  @Transactional(readOnly = true)
  public Capability getByAnd(String field1, Object value1, String field2, Object value2) {
    return capabilityDao.getByAnd(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public Capability getByOr(String field1, Object value1, String field2, Object value2) {
    return capabilityDao.getByOr(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> listBy(String field, Object value) {
    return capabilityDao.listBy(field, value);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> listByAnd(String field1, Object value1, String field2, Object value2) {
    return capabilityDao.listByAnd(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> listByOr(String field1, Object value1, String field2, Object value2) {
    return capabilityDao.listByOr(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> pageBy(String field, Object value, Pager page) {
    return capabilityDao.pageBy(field, value, page);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> pageByAnd(String field1, Object value1, String field2, Object value2,
      Pager page) {
    return capabilityDao.pageByAnd(field1, value1, field2, value2, page);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Capability> pageByOr(String field1, Object value1, String field2, Object value2,
      Pager page) {
    return capabilityDao.pageByOr(field1, value1, field2, value2, page);
  }

  @Override
  public void deleteByTime(String startTime, String endTime) {
    capabilityDao.deleteByTime(startTime, endTime);
  }

  @Override
  public void collectData() throws UnknownHostException, SigarException {
    String now = DateUtil.now();
    String ip = SystemUtil.getIpAddress();
    collectCpuData(now, ip);
    collectMemoryData(now, ip);
    collectDiskData(now, ip);
    collectNetworkData(now, ip);
  }

  /**
   * 收集网络流量数据
   * 
   * @param now
   * @param ip
   */
  private void collectNetworkData(String now, String ip) throws SigarException {
    Map<String, Double> ioMap = SigarUtil.getInstance().getNetworkIO();
    ioMap.forEach((key, value) -> {
      Capability capability = new Capability();
      capability.setIp(ip);
      capability.setDateTime(now);
      capability.setType(CapabilityType.NETWORK.getName());
      capability.setSubType(key);
      capability.setValue(value);
      this.add(capability);
    });
  }

  /**
   * 收集磁盘数据
   * 
   * @param now
   * @param ip
   */
  private void collectDiskData(String now, String ip) throws SigarException {
    Map<String, Double> valueMap = SigarUtil.getInstance().getFileSystemUsedPercent();
    valueMap.forEach((key, value) -> {
      value = MathUtil.scale(value, 2);
      Capability capability = new Capability();
      capability.setIp(ip);
      capability.setDateTime(now);
      capability.setType(CapabilityType.DISK.getName());
      capability.setSubType(key);
      capability.setValue(value);
      this.add(capability);
    });
  }

  /**
   * 收集内存数据
   * 
   * @param now
   * @param ip
   */
  private void collectMemoryData(String now, String ip) throws SigarException {
    double value = SigarUtil.getInstance().getMemoryUsedPercent();
    value = MathUtil.scale(value, 2);
    Capability capability = new Capability();
    capability.setIp(ip);
    capability.setDateTime(now);
    capability.setType(CapabilityType.MEMORY.getName());
    capability.setValue(value);
    capability.setSubType(StringUtils.EMPTY);
    this.add(capability);
  }

  /**
   * 收集cpu数据
   * 
   * @param now
   * @param ip
   * @throws SigarException
   */
  private void collectCpuData(String now, String ip) throws SigarException {
    double value = SigarUtil.getInstance().getCpuUsedPercent();
    value = MathUtil.scale(value, 2);
    Capability capability = new Capability();
    capability.setIp(ip);
    capability.setDateTime(now);
    capability.setType(CapabilityType.CPU.getName());
    capability.setValue(value);
    capability.setSubType(StringUtils.EMPTY);
    this.add(capability);
  }

}
