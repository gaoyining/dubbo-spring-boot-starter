package com.alibaba.dubbo.spring.boot.listener;

import java.util.Set;

import com.alibaba.dubbo.spring.boot.bean.ClassIdBean;
import com.alibaba.dubbo.spring.boot.bean.DubboSpringBootStarterConstants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.listener.InvokerListenerAdapter;

/**
 * Dubbo client invoker listener
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate
public class ConsumerSubscribeListener extends InvokerListenerAdapter {
  /**
   * subscribe interfaces
   */
  public static final Set<ClassIdBean> SUBSCRIBEDINTERFACES_SET =
      new ConcurrentHashSet<ClassIdBean>();

  public void referred(Invoker<?> invoker) throws RpcException {
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    SUBSCRIBEDINTERFACES_SET.add(classIdBean);
  }

  public void destroyed(Invoker<?> invoker) {
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    SUBSCRIBEDINTERFACES_SET.remove(classIdBean);
  }
}
