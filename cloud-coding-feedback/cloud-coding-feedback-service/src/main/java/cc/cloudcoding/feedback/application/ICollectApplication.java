package cc.cloudcoding.feedback.application;

public interface ICollectApplication {
    void addCollect(String objectId, String userId);

    void removeCollect(String objectId, String userId);

    boolean isCollectdByUser(String objectId, String userId);

    Long countCollect(String objectId);
}
