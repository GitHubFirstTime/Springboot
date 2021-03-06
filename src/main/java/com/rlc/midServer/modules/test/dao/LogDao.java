/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.midServer.modules.test.dao;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.midServer.common.persistence.CrudDao;
import com.rlc.midServer.common.persistence.annotation.MyBatisDao;
import com.rlc.midServer.modules.test.entity.Log;

/**
 * 日志DAO接口
 * @author xx
 * @version 2014-05-16
 */
@DS("fmbdb")
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

	public void empty();
}
