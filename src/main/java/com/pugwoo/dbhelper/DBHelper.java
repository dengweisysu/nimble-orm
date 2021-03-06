package com.pugwoo.dbhelper;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.pugwoo.dbhelper.exception.NullKeyValueException;
import com.pugwoo.dbhelper.model.PageData;

/**
 * 2015年8月17日 18:18:57
 * @author pugwoo
 */
public interface DBHelper {
	
	/**
	 * 手动回滚@Transactional的事务。
	 * 对于已知需要回滚的动作，我更推荐主动调用让其回滚，而非抛出RuntimeException
	 */
	void rollback();
	
	/**
	 * 设置SQL执行超时的WARN log，超时时间为1秒
	 * @param timeMS 毫秒
	 */
	void setTimeoutWarningValve(long timeMS);
	
	// 几个常用的jdbcTemplate的方法，目的是用dbHelper时可以使用in (?)传入list的参数
	
	/**
	 * jdbcTemplate方式查询对象，clazz不需要Dbhelper的@Table等注解。
	 * @param clazz 一般是Long,String等基本类型
	 * @param sql 必须是完整的sql
	 * @param args
	 * @return
	 */
	<T> T queryForObject(Class<T> clazz, String sql, Object... args);
	
	/**
	 * jdbcTemplate方式查询对象
	 * @param sql 必须是完整的sql
	 * @param args
	 * @return
	 */
	SqlRowSet queryForRowSet(String sql, Object... args);
	
	/**
	 * jdbcTemplate方式查询对象
	 * @param sql 必须是完整的sql
	 * @param args
	 * @return
	 */
	Map<String, Object> queryForMap(String sql, Object... args);
	
	/**
	 * 查询多列的结果
	 * @param sql 必须是完整的sql
	 * @param args
	 * @return
	 */
	List<Map<String, Object>> queryForList(String sql, Object... args);
	
	/**
	 * 查询多列结果
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	<T> List<T> queryForList(Class<T> clazz, String sql, Object... args);
	
	// END
	
	/**
	 * 通过T的主键，将数据查出来并设置到T中
	 * @param t 值设置在t中
	 * @return 存在返回true，否则返回false
	 */
	<T> boolean getByKey(T t) throws NullKeyValueException;
	
	/**
	 * 适合于只有一个Key的情况
	 * @param clazz
	 * @param keyValue
	 * @return 如果不存在则返回null
	 */
    <T> T getByKey(Class<?> clazz, Object keyValue) throws NullKeyValueException;
    
    /**
     * 通过多个key查询对象
     * @param clazz
     * @param keyValues
     * @return 返回的值是LinkedHashMap对象，按照keyValues的顺序来，但如果key不存在，那么不会再返回值的map key中
     */
    <T, K> Map<K, T> getByKeyList(Class<?> clazz, List<K> keyValues);
	
	/**
	 * 适合于只有一个或多个Key的情况
	 * @param clazz
	 * @param keyMap
	 * @return 如果不存在则返回null
	 */
	<T> T getByKey(Class<?> clazz, Map<String, Object> keyMap) throws NullKeyValueException;
	
	/**
	 * 查询列表，没有查询条件
	 * @param clazz
	 * @param page 从1开始
	 * @param pageSize
	 * @return 返回的data不会是null
	 */
	<T> PageData<T> getPage(Class<T> clazz, int page, int pageSize);

	/**
	 * 查询列表，postSql可以带查询条件
	 * @param clazz
	 * @param page 从1开始
	 * @param pageSize
	 * @param postSql 包含where关键字起的后续SQL语句
	 * @return 返回的data不会是null
	 */
	<T> PageData<T> getPage(Class<T> clazz, int page, int pageSize,
			String postSql, Object... args);
	
	/**
	 * 计算总数
	 * @param clazz
	 * @return
	 */
	<T> int getCount(Class<T> clazz);
	
	/**
	 * 计算总数
	 * @param clazz
	 * @param postSql
	 * @param args
	 * @return
	 */
	<T> int getCount(Class<T> clazz, String postSql, Object... args);
	
	/**
	 * 查询列表，没有查询条件；不查询总数
	 * @param clazz
	 * @param page 从1开始
	 * @param pageSize
	 * @return 返回的data不会是null
	 */
	<T> PageData<T> getPageWithoutCount(Class<T> clazz, int page, int pageSize);
	
	/**
	 * 查询列表，postSql可以带查询条件；不查询总数
	 * @param clazz
	 * @param page 从1开始
	 * @param pageSize
	 * @param postSql 包含where关键字起的后续SQL语句
	 * @return 返回的data不会是null
	 */
	<T> PageData<T> getPageWithoutCount(Class<T> clazz, int page, int pageSize,
			String postSql, Object... args);
	
	/**
	 * 查询列表，查询所有记录，如果数据量大请慎用
	 * @param clazz
	 * @return 返回不会是null
	 */
	<T> List<T> getAll(Class<T> clazz);
	
	/**
	 * 查询列表，查询所有记录，如果数据量大请慎用
	 * @param clazz
	 * @return 返回不会是null
	 */
	<T> List<T> getAll(Class<T> clazz, String postSql, Object... args);
	
	/**
	 * 查询一条记录，如果有多条，也只返回第一条。该方法适合于知道返回值只有一条记录的情况。
	 * @param clazz
	 * @return 如果不存在则返回null
	 */
	<T> T getOne(Class<T> clazz);
	
	/**
	 * 查询一条记录，如果有多条，也只返回第一条。该方法适合于知道返回值只有一条记录的情况。
	 * @param clazz
	 * @param postSql
	 * @param args
	 * @return 如果不存在则返回null
	 */
	<T> T getOne(Class<T> clazz, String postSql, Object... args);
	
	
	
	/**
	 * 插入一条记录，返回数据库实际修改条数。<br>
	 * 如果包含了自增id，则自增Id会被设置。<br>
	 * 【注】只插入非null的值，如要需要插入null值，则用insertWithNull。
	 * @param t
	 * @return
	 */
	<T> int insert(T t);
	
	/**
	 * 插入一条记录，返回数据库实际修改条数。<br>
	 * 如果包含了自增id，则自增Id会被设置。<br>
	 * 【注】只插入非null的值，如要需要插入null值，则用insertWithNullWhereNotExist。
	 * whereSql是判断条件，当条件成立时，不插入；当条件不成立时，插入。
	 * @param t
	 * @param whereSql 不含where关键字，不能包含order/group/limit等后续语句
	 * @param args
	 * @return
	 */
	<T> int insertWhereNotExist(T t, String whereSql, Object... args);
	
	/**
	 * 插入一条记录，返回数据库实际修改条数。<br>
	 * 如果包含了自增id，则自增Id会被设置。
	 * @param t
	 * @return
	 */
	<T> int insertWithNull(T t);
	
	/**
	 * 插入一条记录，返回数据库实际修改条数。<br>
	 * 如果包含了自增id，则自增Id会被设置。<br>
	 * whereSql是判断条件，当条件成立时，不插入；当条件不成立时，插入。
	 * @param t
	 * @param whereSql 不含where关键字，不能包含order/group/limit等后续语句
	 * @param args
	 * @return
	 */
	<T> int insertWithNullWhereNotExist(T t, String whereSql, Object... args);
	
	/**
	 * 插入几条数据，通过拼凑成一条sql插入
	 *【重要】批量插入不支持回设自增id。批量插入会把所有属性都插入，不支持只插入非null的值。
	 * (说明:这个方法之前叫insertInOneSQL)
	 * @param list
	 * @return 返回影响的行数
	 */
	<T> int insertWithNullInOneSQL(List<T> list);
		
	/**
	 * 更新单个实例数据库记录，必须带上object的key，包含更新null值的字段
	 * @param t
	 * @return 返回数据库实际修改条数
	 * @throws NullKeyValueException
	 */
	<T> int updateWithNull(T t) throws NullKeyValueException;
	
	/**
	 * 带条件的更新单个对象，必须带上object的key，主要用于mysql的update ... where ...这样的CAS修改
	 * 
	 * @param t
	 * @param postSql where及后续的sql，包含where关键字
	 * @param args
	 * @return 返回数据库实际修改条数
	 * @throws NullKeyValueException
	 */
	<T> int updateWithNull(T t, String postSql, Object... args) throws NullKeyValueException;
	
	/**
	 * 更新单条数据库记录,必须带上object的key。【只更新非null字段】
	 * @param t
	 * @return 返回数据库实际修改条数
	 * @throws NullKeyValueException
	 */
	<T> int update(T t) throws NullKeyValueException;
	
	/**
	 * 更新单条数据库记录,必须带上object的key，主要用于mysql的update ... where ...这样的CAS修改。
	 * 【只更新非null字段】
	 * 
	 * @param t
	 * @param postSql where及后续的sql，包含where关键字
	 * @param args
	 * @return 返回数据库实际修改条数
	 * @throws NullKeyValueException
	 */
	<T> int update(T t, String postSql, Object... args) throws NullKeyValueException;
	
	/**
	 * 更新数据库记录，更新包含null的字段，返回数据库实际修改条数。
	 * 【注】批量更新的方法并不会比程序中循环调用int updateNotNull(T t)更快
	 * @param list
	 * @return
	 * @throws NullKeyValueException
	 */
	<T> int updateWithNull(List<T> list) throws NullKeyValueException;
	
	/**
	 * 更新数据库记录，返回数据库实际修改条数。
	 * 【注】批量更新的方法并不会比程序中循环调用int update(T t)更快
	 * 【只更新非null字段】
	 * @param list
	 * @return
	 * @throws NullKeyValueException
	 */
	<T> int update(List<T> list) throws NullKeyValueException;
	
	/**
	 * 删除数据库记录，返回数据库实际修改条数
	 * @param t
	 * @return
	 */
	<T> int deleteByKey(T t) throws NullKeyValueException;
	
	/**
	 * 删除数据库记录，返回实际修改数据库条数，这个接口只支持单个字段是key的情况
	 * @param clazz
	 * @param keyValue
	 * @return
	 * @throws NullKeyValueException
	 */
	<T> int deleteByKey(Class<?> clazz, Object keyValue) throws NullKeyValueException;
		
	/**
	 * 自定义条件删除数据
	 * @param clazz
	 * @param postSql 必须提供，必须写where
	 * @param args
	 * @return
	 */
	<T> int delete(Class<T> clazz, String postSql, Object... args);
}
