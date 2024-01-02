package cn.edu.hcnu.gateway.util;

import java.util.Arrays;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 3:44
 */
public class PathMatcher {
    public static void main(String[] args) {
        String path = "/cloud-coding-captcha/doc.html";
        boolean isMatch = matchPath("/**/doc.html", path);
        System.out.println(isMatch);
    }

    public static boolean matchPath(String pattern, String path) {
        if (pattern.equals("**")) {
            return true; // 匹配任意层路径
        }

        if (pattern.startsWith("**/")) {
            String subPattern = pattern.substring(3);
            return matchSubPath(subPattern, path);
        }

        if (pattern.endsWith("/**")) {
            String subPattern = pattern.substring(0, pattern.length() - 3);
            return matchSubPath(subPattern, path);
        }

        return pattern.equals(path);
    }

    public static boolean matchSubPath(String subPattern, String subPath) {
        if (subPath.isEmpty()) {
            return false;
        }

        String[] subPatterns = subPattern.split("/");
        String[] subPaths = subPath.split("/");

        if (subPatterns.length > subPaths.length) {
            return false;
        }

        int patternIndex = 0;
        int pathIndex = 0;

        while (patternIndex < subPatterns.length && pathIndex < subPaths.length) {
            String currentPattern = subPatterns[patternIndex];
            String currentPath = subPaths[pathIndex];

            if (currentPattern.equals("**")) {
                break;
            }

            if (!currentPattern.equals(currentPath)) {
                return false;
            }

            patternIndex++;
            pathIndex++;
        }

        if (patternIndex == subPatterns.length) {
            return pathIndex == subPaths.length; // 所有路径段匹配完成
        }

        if (pathIndex == subPaths.length) {
            return subPatterns[patternIndex].equals("**"); // 剩余通配符匹配任意层路径
        }

        String subSubPattern = String.join("/", Arrays.copyOfRange(subPatterns, patternIndex, subPatterns.length));
        String subSubPath = String.join("/", Arrays.copyOfRange(subPaths, pathIndex, subPaths.length));

        return matchPath(subSubPattern, subSubPath);
    }
}