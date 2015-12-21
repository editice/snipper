package org.editice.snipper.web.util;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * 云标签列表
 * <p>
 * 后期考虑由系统引擎处理
 * </p>
 *
 * @author tinglang(editice@gmail.com) on 15/12/21.
 */
public class TagUtil {

    static List<String> blogTags = Lists.newArrayList();

    static {
        add(
                "java",
                "网站",
                "建模",
                "多线程",
                "阿里云服务器",
                "虚拟机"
        );
    }

    static void add(String... pairs) {
        Collections.addAll(blogTags, pairs);
    }

    public static List<String> getBlogTags() {
        return blogTags;
    }

}
