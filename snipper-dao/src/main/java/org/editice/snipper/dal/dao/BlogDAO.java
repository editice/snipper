package org.editice.snipper.dal.dao;

import org.editice.snipper.client.domain.BlogDO;

import java.util.List;

/**
 * @author tinglang(editice@gmail.com) on 15/12/10.
 */
public interface BlogDAO {
    /**
     * 根据文件路径读取文件内容
     * <p>
     * 编码：utf-8
     * </p>
     *
     * @param path
     * @return
     */
    String getFileContent(String path);

    /**
     * 根据博客id获取博客的内容，包括作者，创建时间，摘要等
     * <p>
     * 注意，仅该方法能够获取到博客的具体内容
     * </p>
     *
     * @param id
     * @return
     */
    BlogDO queryBlogsById(int id);

    /**
     * 根据关键词查询博客列表，支持分页；无法查询到博客的具体内容
     *
     * @param keyword
     * @return
     */
    List<BlogDO> queryBlogsByKeyword(String keyword, int index, int size);

    /**
     * 统计特定关键词所所对应的博客数量，keyword为null时统计所有的博客
     *
     * @param keyword
     * @return
     */
    int countBlogsByKeyword(String keyword);

    /**
     * 根据时间日期逆序排列获取相应的博客列表
     *
     * @param index
     * @param size
     * @return
     */
    List<BlogDO> queryBlogs(int index, int size);

    /**
     * 插入一条新博客，返回值为博客id，若插入失败，返回-1
     *
     * @param blog
     * @return
     */
    int insertBlog(BlogDO blog);

    /**
     * 更新指定博客id的博客信息，可支持更新字段包括：
     * <p>
     * 1. 博客内容
     * 2. 博客标题
     * </p>
     *
     * @param id
     * @param blog
     * @return
     */
    boolean updateBlog(int id, BlogDO blog);
}
