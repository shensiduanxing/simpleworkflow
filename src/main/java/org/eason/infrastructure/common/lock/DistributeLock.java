package org.eason.infrastructure.common.lock;

import java.util.concurrent.ExecutionException;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

import org.apache.log4j.Logger;

public class DistributeLock {
	private Logger logger = Logger.getLogger(DistributeLock.class);
	private MemcachedClient memcachedClient;
	private int RETRY_INTERVAL = 10; //Unit Sec
	
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
	public boolean lock(String lockName){
		OperationFuture<Boolean> successFuture = memcachedClient.add(lockName, 0, "locked");
		boolean success = false;
		try {
			success = successFuture.get();
		} catch (InterruptedException e) {
			logger.error(e);
		} catch (ExecutionException e) {
			logger.error(e);
		}
		if(success){
			logger.debug(String.format("add lock %s successfully", lockName));
		}else{
			//logger.debug(String.format("add lock %s failed", lockName));
		}
		return success;
	}
	
	
	/**
	 * 
	 * @param lockName
	 * @param lockTimeSpan
	 * @param timeout   Unit Secs
	 * @return
	 */
	public boolean lock(String lockName, int lockTimeSpan, int timeout){
		//SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		//String lockValue = String.format("locked@%s", dateFormatter.format(new Date()));
		boolean success = false;

		long timeoutTime = System.currentTimeMillis() + timeout*1000;
		
		while(timeoutTime - System.currentTimeMillis() > 0 ){
			//logger.debug(String.format("try get lock %s", lockName));
			String lockValue = System.currentTimeMillis()+"";

			try {
				OperationFuture<Boolean> successFuture = memcachedClient.add(lockName, lockTimeSpan, lockValue);
				success = successFuture.get();
				logger.debug(String.format("lock success = %s, lockValue is %s", success, lockValue));
			} catch (InterruptedException e) {
				logger.error(String.format("Lock InterruptedException: %s ", e));
			} catch (ExecutionException e) {
				logger.error(String.format("Lock ExecutionException: %s ", e));
			} catch (Exception e){
				logger.error(e);
			}
			if(success){
				logger.debug(String.format("get lock %s successfully, lockValue=%s", lockName, lockValue));
				break;
			}else{
				//logger.debug(String.format("get lock %s failed", lockName));
				String strLockTime = (String)memcachedClient.get(lockName);
				logger.debug("strLockTime=" + strLockTime);
				if(strLockTime!=null) {
					long lockTime = Long.parseLong(strLockTime);
					long lockedTime = lockTime - System.currentTimeMillis();
					if(lockedTime > lockTimeSpan*1000){
						logger.debug(String.format("add lock %s failed, forcelly remove the lock that locked for %s mili secs", lockName, lockedTime));
						unlock(lockName);
					}
				}else{
					//logger.debug("strLockTime=" + strLockTime);
				}
			}
			
			try{
				Thread.sleep(RETRY_INTERVAL*1000);
			}catch(Exception e){
				logger.error(e);
			}
		}
		
		return success;
	}
	
	public boolean unlock(String lockName){
		boolean success = false;
		
		try {
			OperationFuture<Boolean> successFuture = memcachedClient.delete(lockName);
			success = successFuture.get();
			if(success){
				//logger.debug(String.format("release lock %s successfully", lockName));
			}else{
				//logger.debug(String.format("release lock %s failed", lockName));
			}
		} catch (InterruptedException e) {
			logger.error(e);
		} catch (ExecutionException e) {
			logger.error(e);
		}catch(Exception e){
			logger.error(e);
		}
		return success;
	}
}
