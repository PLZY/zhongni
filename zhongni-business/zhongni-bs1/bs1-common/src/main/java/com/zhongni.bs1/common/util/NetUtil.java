package com.zhongni.bs1.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtil {
    private static final Logger logger = LoggerFactory.getLogger(NetUtil.class);
    /**
     * 测试ip及端口连通性
     *
     * @param host
     * @param port
     * @param timeout
     * @return boolean
     */
    public static boolean checkIpAndPort(String host, int port, int timeout)
    {
        Socket s = new Socket();
        try
        {
            s.connect(new InetSocketAddress(host, port), timeout);
            return true;
        }
        catch (IOException e)
        {
            logger.error("{}:{} can not link success", host, port, e);
            return false;
        }
        finally
        {
            try
            {
                s.close();
            }
            catch (IOException ex)
            {
                logger.error("Socket close is IOException : {}", ex.getMessage(), ex);
            }
        }
    }

    //测试方法
    public static void main(String[] args) {
//        String host = "127.0.0.1";
//        int port = 8080;
//        int timeOut = 3000;
//        //testIpAndPort(host, port, timeOut);
        findPort("127.0.0.1", 15000);
    }

    public static Integer findPort(String host, Integer startPort)
    {
        Integer canUsePort = null;
        Socket socket = null;
        int port = startPort;
        // 从15000端口开始查找，如果未被使用则返回，否则+1之后继续查找。如果连续100个端口均被占用则放弃
        while (port < startPort + 100)
        {
            try
            {
                logger.info("check {} port status", port);
                socket = new Socket(host, port);
                logger.info("port {} has been used, need find other port...", port);
                port++;
            }
            catch (UnknownHostException e)
            {
                logger.error("Exception occured", e);
                break;
            }
            catch (IOException e)
            {
                logger.debug("IOException occured: {}", e.getMessage(), e);
                // 连接出错认为当前端口未被使用
                canUsePort = port;
                break;
            }
            finally
            {
                if(socket != null)
                {
                    try
                    {
                        socket.close();
                    }
                    catch (IOException ex)
                    {
                        logger.error("socket.close is IOException", ex);
                    }
                }
            }
        }

        return canUsePort;
    }
}