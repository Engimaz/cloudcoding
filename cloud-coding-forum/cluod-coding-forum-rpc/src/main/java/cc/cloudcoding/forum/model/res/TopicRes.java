package cc.cloudcoding.forum.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 9:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRes {
    private String id;

    /**
     * 创建者
     */
    private String userId;

    /**
     * 专栏名称
     */
    private String name;

    /**
     * 专栏描述
     */
    private String description;

    /**
     * 专栏标签
     */
    private String label;

    /*
     * 文章类型
     */
    private String type;

    private List<ArticleRes> articles;
}
