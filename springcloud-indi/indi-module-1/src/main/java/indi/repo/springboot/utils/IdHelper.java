package indi.repo.springboot.utils;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 全新的雪花漂移算法（如下简称本算法），让ID更短、生成速度更快。
 * 
 * 1.整形数字，随时间单调递增（不必定连续），长度更短，用50年都不会超过 js Number类型最大值。（默认配置 WorkerId
 * 是6bit，自增数是6bit）
 * 
 * 2.速度更快，是传统雪花算法的2-5倍，0.1秒可生成50万个。（i7笔记本，默认算法配置6bit+6bit）
 * 
 * 3.支持时间回拨处理。好比服务器时间回拨1秒，本算法能自动适应生成临界时间的惟一ID。
 * 
 * 4.支持手工插入新ID。当业务须要在历史时间生成新ID时，用本算法的预留位能生成5000个每秒。
 * 
 * 5.漂移时能外发通知事件。让调用方确切知道算法漂移记录，Log并发调用量。
 * 
 * 6.不依赖任何外部缓存和数据库。（但 WorkerId 必须由外部指定）
 * 
 * @author Tangguo
 *
 */
public class IdHelper {

	/**
	 * 毫秒格式化时间
	 */
	public static final DateTimeFormatter MILLISECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	public static long getId() {
		return YitIdHelper.nextId();
	}

	public static String getIdStr() {
		return String.valueOf(YitIdHelper.nextId());
	}

	/**
	 * 格式化的毫秒时间
	 */
	public static String getMillisecond() {
		return LocalDateTime.now().format(MILLISECOND);
	}

	/**
	 * 时间 ID = Time + ID
	 * <p>
	 * 例如：可用于商品订单 ID
	 * </p>
	 */
	public static String getTimeId() {
		return getMillisecond() + getId();
	}

	/**
	 * 使用ThreadLocalRandom获取UUID获取更优的效果 去掉"-"
	 */
	public static String get32UUID() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return new UUID(random.nextLong(), random.nextLong()).toString().replace("-", "");
	}

	/**
	 * 有参构造器
	 *
	 * @param workerId 工作机器 ID
	 */
	public static void initSequence(Short workerId) {
		YitIdHelper.setIdGenerator(new IdGeneratorOptions(workerId));
	}
}
