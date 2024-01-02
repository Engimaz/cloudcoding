package cn.edu.hcnu.forum.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 19:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReq {
    private String id;

    private String topicId;

    /**
     * 作者
     */
    private String userId;

    /**
     * 文章标题
     */
    private String title;
    private String avatar;

    private String content;

    /**
     * 文章 标签
     */
    private String label;
    private String status;


}
