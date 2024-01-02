package cn.edu.hcnu.forum.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 19:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleCommand {

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


}
