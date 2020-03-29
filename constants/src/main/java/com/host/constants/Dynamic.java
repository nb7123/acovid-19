package com.host.constants;

/**
 * --------------------------------------------------------------|
 *                                                               |
 *                          _________-----_____                  |
 *            _____------           __      ----_                |
 *   _____----             ___------              \              |
 *        ----________        ----                 \             |
 *                    -----__    |             _____)            |
 *                         __-                /     \            |
 *             _______-----    ___--          \    /)\           |
 *     --------_______      ---____            \__/  /           |
 *                    -----__    \ --    _          /\           |
 *                           --__--__     \_____/   \_/\         |
 *                                   ----|   /          |        |
 *                                       |  |___________|        |
 *                                       |  | ((_(_)| )_)        |
 *                                       |  \_((_(_)|/(_)        |
 *                                       \             (         |
 *                                        \_____________)        |
 *                              @@@@@                            |
 * --------------------------------------------------------------|
 *       Author[michael]          |       Create[2020-02-21]
 * --------------------------------------------------------------
 * Description: Dynamic class implementation config
 * --------------------------------------------------------------
 */
public class Dynamic {
    /**
     * Host nav graph loader class implementation
     */
        public static final String NAV_GRAPH_LOADER_IMP_CLASS_NAME = "com.host.navgraph.MainNavHostFragmentFactory";

    public static String getNavGraphApkName(boolean debug) {
        if (debug) {
            return "navgraph-debug.apk";
        }

        return "navgraph-release.apk";
    }

    public static String getDynamicFeatureApk(String index, boolean debug) {
        if (debug) {
            return "plugin" + index + "-debug.apk";
        }

        return "plugin" + index + "-release.apk";
    }
}
