package cn.edu.hcnu.forum.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 20:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRes {
    private Long id;

    private String topicId;

    /**
     * 作者
     */
    private String userId;

    /**
     * 文章标题
     */
    private String title;

    private String content;
    private String avatar;


    /**
     * 文章 标签
     */
    private String label;

    private String status;


    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
