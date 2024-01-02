package cn.edu.hcnu.feedback.application;

public interface ILikeApplication {
    void addLike(String objectId, String userId);

    void removeLike(String objectId, String userId);

    boolean isLikedByUser(String objectId, String userId);

    Long countLike(String objectId);
}
