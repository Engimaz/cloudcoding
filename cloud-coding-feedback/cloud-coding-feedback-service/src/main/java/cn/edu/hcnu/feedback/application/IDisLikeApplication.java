package cn.edu.hcnu.feedback.application;

public interface IDisLikeApplication {
    void addDisLike(String objectId, String userId);

    void removeDisLike(String objectId, String userId);

    boolean isDisLikedByUser(String objectId, String userId);

    Long countDisLike(String objectId);
}
