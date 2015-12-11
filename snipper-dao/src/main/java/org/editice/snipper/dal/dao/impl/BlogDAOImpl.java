package org.editice.snipper.dal.dao.impl;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.editice.snipper.client.domain.BlogDO;
import org.editice.snipper.dal.IbatisParamMap;
import org.editice.snipper.dal.dao.BlogDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * @author tinglang(editice@gmail.com) on 15/12/10.
 */
@SuppressWarnings("ALL")
public class BlogDAOImpl extends SqlMapClientDaoSupport implements BlogDAO {
    private static final Logger log = LoggerFactory.getLogger(BlogDAOImpl.class);

    @Override
    public String getFileContent(String path) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            log.error("read file fail, path=" + path, e);
            return null;
        }
        return sb.toString();
    }

    @Override
    public BlogDO queryBlogsById(int id) {
        IbatisParamMap map = new IbatisParamMap("id", id);
        List<BlogDO> list = getSqlMapClientTemplate().queryForList("blog.searchById", map);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<BlogDO> queryBlogsByKeyword(String keyword, int index, int size) {
        IbatisParamMap map = new IbatisParamMap("keyword", keyword, index, size);
        List<BlogDO> list = getSqlMapClientTemplate().queryForList("blog.searchByKeyword", map);
        return list;
    }

    @Override
    public int countBlogsByKeyword(String keyword) {
        IbatisParamMap map = new IbatisParamMap("keyword", keyword);
        int size= (int) getSqlMapClientTemplate().queryForObject("blog.countTag", map);
        return size;
    }

    @Override
    public List<BlogDO> queryBlogs(int index, int size) {
        IbatisParamMap map = new IbatisParamMap();
        map.setMySQLLimit(index,size);

        List<BlogDO> list = getSqlMapClientTemplate().queryForList("blog.search", map);
        return list;
    }

    @Override
    public int insertBlog(final BlogDO blog) {
        if (blog == null) {
            return -1;
        }
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor)
                    throws SQLException {
                executor.startBatch();
                executor.insert("blog.insert", blog);
                executor.executeBatch();
                return 0;
            }
        });
        return 0;
    }

    @Override
    public boolean updateBlog(int id, BlogDO blog) {
        return false;
    }
}
