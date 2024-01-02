package cn.edu.hcnu.dictionary.rpc;


import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;

/**
 * 字典服务
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/13
 */
public interface DictionaryService {
    /**
     * 根据标签获取字典
     *
     * @param label 标签
     */
    DictionaryDTO getDictionaryByLabel(String label);


    DictionaryDTO getDictionaryByValue(String value);

    /**
     * @param id: 字典id
     * @description: 根据id获取字典数据
     * @return: {@link DictionaryDTO}
     * @author: 不才小马
     * @time: 2023/9/2 22:46
     */
    DictionaryDTO getDictionaryById(Long id);


}
