package com.zhongni.bs1.common.util;

import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class RandomUtil {
    private static final SnowFlake idWorker = new SnowFlake(0, 0);

    private static final String[] codeArr = {"A","B","1","C","D","2","E","F","3","G","H","4",
            "J","6","K","L","7","M","N","8","P","Q","9","R","S","5","T","U","V","W","X","Y","Z"};

    private static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
    private static final Random random = new Random();

    /**
     * 生成订单流水号（可以保证100年内不重复，一秒内最多支持9999个订单）
     * @param orderType 订单类型
     * @return 【业务编码】 + 【年的后2位+月+日】+【从今天零点到当前时间经过的秒数 5位】+【四位自增数字】
     */
    public static String generateOrderNo(String orderType){
        // 当前时间
        LocalDateTime now  = LocalDateTime.now();
        //当天的0点整
        LocalDateTime startDateTime = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        //计算当前时间走过的秒
        Duration duration = Duration.between(startDateTime, now);
        long differSecond = duration.toMillis() / 1000;
        //格式化当前时间为【年的后2位+月+日】
        String formateDateStr = now.format(DateTimeFormatter.ofPattern("yyMMdd"));
        // 组装流水号块【年的后2位+月+日+当前时间与今天零点相差的秒数】，秒的长度不足补充0
        String numberBlock = formateDateStr + StringUtils.leftPad(String.valueOf(differSecond), 5, '0');

        //获取【业务编码】 + 【年的后2位+月+日+秒】，作为自增key；
        String prefixOrder = orderType + numberBlock;
        return prefixOrder + StringUtils.leftPad(String.valueOf(CacheUtil.autoInc(prefixOrder)), 4, '0');
    }




    /**
     * 随机验证码
     */
    public static String generateVerificationCode()
    {
        StringBuilder verificationNumber = new StringBuilder();
        for (int i = 0; i < 6; i++)
        {
            int v = random.nextInt(33);
            verificationNumber.append(codeArr[v]);
        }
        return verificationNumber.toString();
    }

    /**
     * 1000W数据量下大约会出现1000个重复，此方法需要配合mysql进行去重校验
     */
    public static String generateInvitationCode()
    {
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 6; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }

        return sb.toString();
    }

    /**
     * 雪花算法直接生成的唯一流水号，可用于生成订单流水
     */
    public static Long generateSerialNumber(){
        return idWorker.nextId();
    }

    /**
     * 用于生成唯一商券编码：券类型-下单日期-18位随机数（有一定自增规律）
     */
    public static String generateTicketCode(String ticketType){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = ticketType + "-" + sdf.format(new Date()) + "-";
        return format + generateSerialNumber();
    }

    public static class SnowFlake {
        /**
         * 机器id所占的位数
         */
        private final long workerIdBits = 5L;

        /**
         * 数据标识id所占的位数
         */
        private final long datacenterIdBits = 5L;

        /**
         * 工作机器ID(0~31)
         */
        private final long workerId;

        /**
         * 数据中心ID(0~31)
         */
        private final long datacenterId;

        /**
         * 毫秒内序列(0~4095)
         */
        private long sequence = 0L;

        /**
         * 上次生成ID的时间截
         */
        private long lastTimestamp = -1L;

        //==============================Constructors=====================================

        /**
         * 构造函数
         *
         * @param workerId     工作ID (0~31)
         * @param datacenterId 数据中心ID (0~31)
         */
        public SnowFlake(long workerId, long datacenterId) {
            //支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
            long maxWorkerId = ~(-1L << workerIdBits);
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            // 支持的最大数据标识id，结果是31
            long maxDatacenterId = ~(-1L << datacenterIdBits);
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        /**
         * 获得下一个ID (该方法是线程安全的)
         *
         * @return SnowflakeId
         */
        public synchronized long nextId() {
            long timestamp = timeGen();

            //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_CLOCK_MOVED_BACKWORDS,
                        String.format("Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }

            // 序列在id中占的位数
            long sequenceBits = 12L;
            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                // 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
                long sequenceMask = ~(-1L << sequenceBits);
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }

            //上次生成ID的时间截
            lastTimestamp = timestamp;
            //移位并通过或运算拼到一起组成64位的ID
            // 开始时间截 (2018-07-03)
            long twepoch = 1530607760000L;
            // 数据标识id向左移17位(12+5)
            long datacenterIdShift = sequenceBits + workerIdBits;
            // 时间截向左移22位(5+5+12)
            long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
            return (((timestamp - twepoch) << timestampLeftShift)
                    | (datacenterId << datacenterIdShift)
                    | (workerId << sequenceBits)
                    | sequence);
        }

        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         *
         * @param lastTimestamp 上次生成ID的时间截
         * @return 当前时间戳
         */
        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        /**
         * 返回以毫秒为单位的当前时间
         *
         * @return 当前时间(毫秒)
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
