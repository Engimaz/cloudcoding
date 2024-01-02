package cn.edu.hcnu.program.domain.service.relation;

import cn.edu.hcnu.program.domain.service.relation.factory.ProgramUserFactory;
import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramUserDomainService {

    @Autowired
    private ProgramUserRepository programUserRepository;

    @Autowired
    private ProgramUserFactory programUserFactory;

    /**
     * 保存一个用户与代码库的关系
     *
     * @param programUser
     * @return
     */
    public ProgramUser save(ProgramUser programUser) {
        ProgramUserPO programUserPO = programUserFactory.createProgramUserPO(programUser);
        boolean save = programUserRepository.save(programUserPO);
        if (save) {
            return programUserFactory.createProgramUser(programUserPO);
        }
        return null;
    }

    /**
     * 更新一个用户与代码库的关系
     *
     * @param programUser
     * @return
     */
    public ProgramUser update(ProgramUser programUser) {
        ProgramUserPO programUserPO = programUserFactory.createProgramUserPO(programUser);
        boolean b = programUserRepository.updateById(programUserPO);
        if (b) {
            return programUserFactory.createProgramUser(programUserPO);
        }
        return null;
    }

    /**
     * 移除一个用户与代码库的关系
     *
     * @param id
     * @return
     */
    public boolean remove(Long id) {
        return programUserRepository.removeById(id);
    }

    /**
     * 获得一个项目的所有成员
     *
     * @return
     */
    public List<ProgramUser> getProgramUserByProgramId(Long programId) {
        List<ProgramUserPO> list = programUserRepository.list(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getProgramId, programId));
        return programUserFactory.createProgramUser(list);
    }

    /**
     * 移除一个项目的所有成员
     *
     * @param programId
     * @return
     */
    public boolean removeProgramAllUser(Long programId) {
        return programUserRepository.remove(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getProgramId, programId));
    }


    public boolean removeByUserIds(List<String> deleteData) {
        long count = deleteData.stream().filter(f ->
                programUserRepository.remove(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getUserId, f))
        ).map(f -> 1).count();
        return deleteData.size() == count;
    }

    public boolean batchSave(List<ProgramUser> relations) {
        return programUserRepository.saveBatch(programUserFactory.createProgramUserPO(relations));
    }

    public List<ProgramUser> queryRelationByUserId(String userId) {
        return programUserFactory.createProgramUser(programUserRepository.list(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getUserId, Long.valueOf(userId))));
    }
}
